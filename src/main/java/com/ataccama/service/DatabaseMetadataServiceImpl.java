package com.ataccama.service;

import com.ataccama.model.ConnectionDto;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Service
public class DatabaseMetadataServiceImpl implements DatabaseMetadataService {

    private final ConnectionService connectionService;
    private final DatabaseDetailMapper databaseDetailMapper;

    public DatabaseMetadataServiceImpl(ConnectionService connectionService, DatabaseDetailMapper databaseDetailMapper) {
        this.connectionService = connectionService;
        this.databaseDetailMapper = databaseDetailMapper;
    }

    @Override
    public DatabaseMetaData getDatabaseDetails(ConnectionDto connectionDto) {
        Connection connection = connectionService.establishConnection(connectionDto);
        try {
            DatabaseMetaData metaData = connection.getMetaData();
//            return databaseDetailMapper.map(metaData);
            return metaData;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage());
        }
    }

}
