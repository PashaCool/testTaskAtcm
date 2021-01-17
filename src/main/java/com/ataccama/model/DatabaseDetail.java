package com.ataccama.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "databasedetails")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "uuid")
public class DatabaseDetail {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uuid;

    @Column(length = 64, name = "innerName", nullable = false)
    private String name;

    @Column(name = "host_name", nullable = false)
    private String hostName;

    /*
    Here object type instead of basic, because in method com.ataccama.service.DatabaseDetailServiceImpl.createDbConnection, when repository invokes exists(Example)
    formed query with condition: where databasede0_.db_port=0 and databasede0_.inner_name=? - in query default basic value
     */
    @Column(name = "db_port", nullable = false)
    private Integer port;

    @Column(name = "database_name", length = 64, nullable = false)
    private String databaseName;

    @Column(name = "user_name", length = 64, nullable = false)
    private String userName;

    @Column(name = "user_password", nullable = false)
    private String password;

}
