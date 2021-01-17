package com.ataccama.service;

import com.ataccama.exception.EstablishConnectionException;
import com.ataccama.model.ColumnDefinition;
import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.model.QueryRequest;
import com.ataccama.model.TableDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
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

    @Override
    public List<TableDefinition> getDatabaseDetails(DatabaseDetailDto connectionDto) {
        var dataSource = connectionService.establishConnection(connectionDto);
        try (Connection connection = dataSource.getConnection()) {
            var metaData = connection.getMetaData();
            return handleMetaData(metaData);
        } catch (SQLException exception) {
            final String url = connectionService.accumulateUrl(connectionDto);
            throw new EstablishConnectionException(url);
        }
    }

    @Override
    public List<Map<String, Object>> executeQuery(QueryRequest request) {
        var connection = connectionService.establishConnection(request.getConnectionDefinition());
        List<Map<String, Object>> result = new ArrayList<>();
        if (connection != null) {
            String sql = "select " + request.getColumns() + " from " + request.getDataSource() + ";";
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);
            result = jdbcTemplate.queryForList(sql);
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
