package com.ataccama.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class DemoConnector {

    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String COLUMN_SIZE = "COLUMN_SIZE";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String IS_NULLABLE = "IS_NULLABLE";
    private static final String IS_AUTOINCREMENT = "IS_AUTOINCREMENT";
    private static final String[] TYPES = {"TABLE"};
    private final DataSource dataSource;

    public DemoConnector(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void smth() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, null, TYPES);
            convertColumns(metaData);
            convertTable(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void convertTable(ResultSet resultSet) throws SQLException {
        while(resultSet.next()) {
            String tableName = resultSet.getString(TABLE_NAME);
        }
    }

    private void convertColumns(DatabaseMetaData metaData) throws SQLException {
        ResultSet columns = metaData.getColumns(null, null, "answers", null);
        while(columns.next()) {
            String columnName = columns.getString(COLUMN_NAME);
            String columnSize = columns.getString(COLUMN_SIZE);
            String datatype = columns.getString(DATA_TYPE);
            String isNullable = columns.getString(IS_NULLABLE);
            String isAutoIncrement = columns.getString(IS_AUTOINCREMENT);
        }
    }

}
