package com.ataccama.service;

import com.ataccama.exception.ConnectionCloseException;
import com.ataccama.exception.ConnectionEstablishExceprion;
import com.ataccama.model.DatabaseDetailDto;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String LOGIN_TIMEOUT = "LoginTimeout";
    private final DatabaseDetailService databaseDetailService;
    @Value("${connection.timeout}")
    private String timeoutDuration;
    private final HashMap<ConnectionDetail, Connection> connectionPool = new HashMap<>();

    public ConnectionServiceImpl(DatabaseDetailService databaseDetailService) {
        this.databaseDetailService = databaseDetailService;
    }

    @Override
    public Connection establishConnection(DatabaseDetailDto connectionDto) {
        String databaseUrl = accumulateUrl(connectionDto);
        try {
            Connection connection = establishConnection(databaseUrl, connectionDto.getUserName(), connectionDto.getPassword());
//            createConnection(connection, connectionDto); //todo for test purpose
            return connection;
        } catch (SQLException throwables) {
            throw new ConnectionEstablishExceprion(databaseUrl, connectionDto.getUserName());
        }
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

    private Connection establishConnection(String databaseUrl, String userName, String password) throws SQLException {
        var connectionDetail = new ConnectionDetail(databaseUrl, userName, password);
        var connection = connectionPool.get(connectionDetail);
        if (connection != null && connectionDetail.isFree()) {
            return connection;
        }
        var props = setProperties(userName, password);
        connection = DriverManager.getConnection(databaseUrl, props);
        connectionDetail.acquireConnection();
        connectionPool.put(connectionDetail, connection);
        return connection;
    }

    private Properties setProperties(String userName, String password) {
        var props = new Properties();
        props.put(USER, userName);
        props.put(PASSWORD, password);
        props.setProperty(LOGIN_TIMEOUT, timeoutDuration);
        return props;
    }

    private String accumulateUrl(DatabaseDetailDto connectionDto) {
        return String.format(URL_TEMPLATE, connectionDto.getHostName(), connectionDto.getPort(), connectionDto.getDatabaseName());
    }

    @PreDestroy
    public void closeConnections() {
        connectionPool.values().forEach(conn -> {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    throw new ConnectionCloseException();
                }
            }
        });
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode(exclude = "isFree")
    private static class ConnectionDetail {
        private final String databaseUrl;
        private final String userName;
        private final String password;
        private final AtomicBoolean isFree = new AtomicBoolean(false);

        void acquireConnection() {
            isFree.compareAndExchange(true, false);
        }

        boolean isFree() {
            return isFree.get();
        }
    }

}
