package com.ataccama.service;

import com.ataccama.model.ColumnDefinition;
import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.model.QueryRequest;
import com.ataccama.model.TableDefinition;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseMetadataServiceImpl implements DatabaseMetadataService {

    private static final String DATABASECHANGELOG = "databasechangelog";
    private static final String DATABASECHANGELOGLOCK = "databasechangeloglock";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String COLUMN_SIZE = "COLUMN_SIZE";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String IS_NULLABLE = "IS_NULLABLE";
    private static final String IS_AUTOINCREMENT = "IS_AUTOINCREMENT";
    private static final String[] TYPES = {"TABLE"};
    private final ConnectionService connectionService;
    private final DatabaseDetailMapper databaseDetailMapper;

    public DatabaseMetadataServiceImpl(ConnectionService connectionService, DatabaseDetailMapper databaseDetailMapper) {
        this.connectionService = connectionService;
        this.databaseDetailMapper = databaseDetailMapper;
    }

    @Override
    public List<TableDefinition> getDatabaseDetails(DatabaseDetailDto connectionDto) {
        var connection = connectionService.establishConnection(connectionDto);
        try {
            var metaData = connection.getMetaData();
            return handleMetaData(metaData);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage());
        }
    }

    @Override
    public Map<String, Object> executeQuery(QueryRequest request) {
        var connection = connectionService.establishConnection(request.getConnectionDefinition());
        Map<String, Object> result = new LinkedHashMap<>();
        if (connection != null) {
            String[] columns = request.getColumns().split(",");
            String sql = "select " + request.getColumns() + " from " + request.getDataSource() + ";";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = preparedStatement.executeQuery();
                var i = 0;
                int length = columns.length;
                while (resultSet.next() && i < length) {
                    String column = columns[i++].trim();//выбирает только первую строку
                    result.put(column, resultSet.getObject(column));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    private List<TableDefinition> handleMetaData(DatabaseMetaData metaData) throws SQLException {
        var resultSet = metaData.getTables(null, null, null, TYPES);
        return convertTable(metaData, resultSet);
    }

    private List<TableDefinition> convertTable(DatabaseMetaData metaData, ResultSet tablesResultSet) throws SQLException {
        List<TableDefinition> dataBaseMetaDataDto = new ArrayList<>();
        while (tablesResultSet.next()) {
            String tableName = tablesResultSet.getString(TABLE_NAME);
            if (isNotServiceTable(tableName)) {
                List<ColumnDefinition> columnDefinitions = convertColumns(metaData, tableName);
                dataBaseMetaDataDto.add(new TableDefinition(tableName, columnDefinitions));
            }
        }
        return dataBaseMetaDataDto;
    }

    private List<ColumnDefinition> convertColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        var columns = metaData.getColumns(null, null, tableName, null);
        var columnDefinitions = new ArrayList<ColumnDefinition>();
        while (columns.next()) {
            var columnDefinition = new ColumnDefinition(columns.getString(COLUMN_NAME), columns.getString(DATA_TYPE), columns.getBoolean(IS_NULLABLE),
                                                        columns.getBoolean(IS_AUTOINCREMENT));
            columnDefinition.setColumnSize(columns.getString(COLUMN_SIZE));
            columnDefinitions.add(columnDefinition);
        }
        return columnDefinitions;
    }

    private boolean isNotServiceTable(String tableName) {
        return !DATABASECHANGELOG.equalsIgnoreCase(tableName) && !DATABASECHANGELOGLOCK.equalsIgnoreCase(tableName);
    }

}
