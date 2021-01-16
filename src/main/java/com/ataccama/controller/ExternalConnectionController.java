package com.ataccama.controller;

import com.ataccama.model.ConnectionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@RestController
@RequestMapping(path = "/api/external", consumes = {"application/json", "text/xml"})
public class ExternalConnectionController {

    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String LOGIN_TIMEOUT = "LoginTimeout";
    @Value("${connection.timeout}")
    private String VALUE;

    @PostMapping("/connect")
    public void connectToDatabase(@RequestBody ConnectionDto connectionDto) {
        Connection connection = null;
        try {
            connection = establishConnection(connectionDto);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private Connection establishConnection(ConnectionDto connectionDto) throws SQLException {
        Properties props = new Properties();
        props.put(USER, connectionDto.getUserName());
        props.put(PASSWORD, connectionDto.getPassword());
        props.setProperty(LOGIN_TIMEOUT, VALUE);
        String dbUrl = String.format(URL_TEMPLATE, connectionDto.getHostName(), connectionDto.getPort(), connectionDto.getDatabaseName());
        return DriverManager.getConnection(dbUrl, props);
    }

}
