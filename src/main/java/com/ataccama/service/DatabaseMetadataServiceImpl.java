package com.ataccama.service;

import com.ataccama.model.DataBaseMetaDataDto;
import com.ataccama.model.DatabaseDetailDto;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class DatabaseMetadataServiceImpl implements DatabaseMetadataService {

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
    public DataBaseMetaDataDto getDatabaseDetails(DatabaseDetailDto connectionDto) {
        Connection connection = connectionService.establishConnection(connectionDto);
        try {
            var metaData = connection.getMetaData();
            var resultSet = metaData.getTables(null, null, null, TYPES);
//            convertColumns(metaData);
            var dataBaseMetaDataDto = convertTable(resultSet);
            return dataBaseMetaDataDto;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage());
        }
    }

    private DataBaseMetaDataDto convertTable(ResultSet resultSet) throws SQLException {
        DataBaseMetaDataDto dataBaseMetaDataDto = new DataBaseMetaDataDto();
        while (resultSet.next()) {
            dataBaseMetaDataDto.addTableName(resultSet.getString(TABLE_NAME));
        }
        return dataBaseMetaDataDto;
    }

    private void convertColumns(DatabaseMetaData metaData) throws SQLException {//todo handle later
        ResultSet columns = metaData.getColumns(null, null, "answers", null);
        while (columns.next()) {
            String columnName = columns.getString(COLUMN_NAME);
            String columnSize = columns.getString(COLUMN_SIZE);
            String datatype = columns.getString(DATA_TYPE);
            String isNullable = columns.getString(IS_NULLABLE);
            String isAutoIncrement = columns.getString(IS_AUTOINCREMENT);
        }
    }

}
