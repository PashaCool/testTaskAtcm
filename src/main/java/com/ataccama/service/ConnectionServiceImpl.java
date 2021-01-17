package com.ataccama.service;

import com.ataccama.model.DatabaseDetailDto;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@RequiredArgsConstructor
@Service
public class ConnectionServiceImpl implements ConnectionService {

    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s";
    private final DatabaseDetailService databaseDetailService;

    @Override
    public DataSource establishConnection(DatabaseDetailDto connectionDto) {
        String databaseUrl = accumulateUrl(connectionDto);
        DataSource connection = establishConnection(databaseUrl, connectionDto.getUserName(), connectionDto.getPassword());
        //            createConnection(connection, connectionDto); //todo for test purpose
        return connection;
    }

    private void createConnection(Connection connection, DatabaseDetailDto connectionDto) {
        if (connection != null) {
            DatabaseDetailDto databaseDetailDto = DatabaseDetailDto.builder()
                                                                   .name(connectionDto.getName())
                                                                   .hostName(connectionDto.getHostName())
                                                                   .port(connectionDto.getPort())
                                                                   .databaseName(connectionDto.getDatabaseName())
                                                                   .userName(connectionDto.getUserName())
                                                                   .password(connectionDto.getPassword())
                                                                   .build();
            databaseDetailService.createNewDbConnection(databaseDetailDto);
        }
    }

    private DataSource establishConnection(String databaseUrl, String userName, String password) {
        HikariConfig config = new HikariConfig();
        config.setUsername(userName);
        config.setPassword(password);
        config.setJdbcUrl(databaseUrl);
        return new HikariDataSource(config);
    }

    private String accumulateUrl(DatabaseDetailDto connectionDto) {
        return String.format(URL_TEMPLATE, connectionDto.getHostName(), connectionDto.getPort(), connectionDto.getDatabaseName());
    }

}
