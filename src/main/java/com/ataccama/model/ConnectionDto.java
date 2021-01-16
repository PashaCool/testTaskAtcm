package com.ataccama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDto {

    private String hostName;
    private int port;
    private String databaseName;
    private String userName;
    private String password;
}
