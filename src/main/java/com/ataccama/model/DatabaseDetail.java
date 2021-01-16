package com.ataccama.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "databasedetails")
@Data
@NoArgsConstructor
public class DatabaseDetail {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uuid;

    @Column(length = 64, name = "innerName")
    private String name;

    @Column(name = "host_name", length = 255, nullable = false)
    private String hostName;

    @Column(name = "db_port", nullable = false)
    private int port;

    @Column(name = "database_name", length = 64, nullable = false)
    private String databaseName;

    @Column(name = "user_name", length = 64, nullable = false)
    private String userName;

    @Column(name = "user_password", length = 255, nullable = false)
    private String password;

}
