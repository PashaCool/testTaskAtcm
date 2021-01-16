package com.ataccama.service;

import com.ataccama.exception.ConnectionEstablishExceprion;
import com.ataccama.model.DatabaseDetailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String LOGIN_TIMEOUT = "LoginTimeout";
    private final DatabaseDetailService databaseDetailService;
    @Value("${connection.timeout}")
    private String TIMEOUT_DURATION;

    public ConnectionServiceImpl(DatabaseDetailService databaseDetailService) {
        this.databaseDetailService = databaseDetailService;
    }

    @Override
    public Connection establishConnection(DatabaseDetailDto connectionDto) {
        String databaseUrl = accumulateUrl(connectionDto);
        try {
            Connection connection = establishConnection(databaseUrl, connectionDto.getUserName(), connectionDto.getPassword());
            createConnection(connection, connectionDto);
            return connection;
        } catch (SQLException throwables) {
            throw new ConnectionEstablishExceprion(databaseUrl, connectionDto.getUserName());
        }
    }

    private void createConnection(Connection connection, DatabaseDetailDto connectionDto) {
        if (connection != null) {
            DatabaseDetailDto build = DatabaseDetailDto.builder()
                                                       .name(connectionDto.getName())
                                                       .hostName(connectionDto.getHostName())
                                                       .port(connectionDto.getPort())
                                                       .databaseName(connectionDto.getDatabaseName())
                                                       .userName(connectionDto.getUserName())
                                                       .password(connectionDto.getPassword())
                                                       .build();
            databaseDetailService.createNewDbConnection(build);
        }
    }

    private Connection establishConnection(String databaseUrl, String userName, String password) throws SQLException {
        Properties props = new Properties();
        props.put(USER, userName);
        props.put(PASSWORD, password);
        props.setProperty(LOGIN_TIMEOUT, TIMEOUT_DURATION);
        return DriverManager.getConnection(databaseUrl, props);
    }

    private String accumulateUrl(DatabaseDetailDto connectionDto) {
        return String.format(URL_TEMPLATE, connectionDto.getHostName(), connectionDto.getPort(), connectionDto.getDatabaseName());
    }

}
