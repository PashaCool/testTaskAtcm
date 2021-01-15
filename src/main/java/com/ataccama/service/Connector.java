package com.ataccama.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class Connector {

    private final DataSource dataSource;

    public Connector(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void smth() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
            ResultSet columns = metaData.getColumns(null,null, "answers", null);
            while(columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnSize = columns.getString("COLUMN_SIZE");
                String datatype = columns.getString("DATA_TYPE");
                String isNullable = columns.getString("IS_NULLABLE");
                String isAutoIncrement = columns.getString("IS_AUTOINCREMENT");
            }
            while(resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
