package com.ataccama.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseDetailDto {

    private String uuid;
    private String name;
    private String hostName;
    private int port;
    private String databaseName;
    private String userName;
    private String password;

}
