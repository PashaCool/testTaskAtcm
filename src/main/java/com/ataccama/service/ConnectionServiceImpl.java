package com.ataccama.service;

import com.ataccama.exception.ConnectionEstablishExceprion;
import com.ataccama.model.ConnectionDto;
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
    @Value("${connection.timeout}")
    private String TIMEOUT_DURATION;

    @Override
    public Connection establishConnection(ConnectionDto connectionDto) {
        String databaseUrl = accumulateUrl(connectionDto);
        try {
            return establishConnection(databaseUrl, connectionDto.getUserName(), connectionDto.getPassword());
        } catch (SQLException throwables) {
            throw new ConnectionEstablishExceprion(databaseUrl, connectionDto.getUserName());
        }
    }

    private Connection establishConnection(String databaseUrl, String userName, String password) throws SQLException {
        Properties props = new Properties();
        props.put(USER, userName);
        props.put(PASSWORD, password);
        props.setProperty(LOGIN_TIMEOUT, TIMEOUT_DURATION);
        return DriverManager.getConnection(databaseUrl, props);
    }

    private String accumulateUrl(ConnectionDto connectionDto) {
        return String.format(URL_TEMPLATE, connectionDto.getHostName(), connectionDto.getPort(), connectionDto.getDatabaseName());
    }

}
