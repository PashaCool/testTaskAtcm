package com.ataccama;

import com.ataccama.service.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationRunner {

    @Autowired
    private Connector connector;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

}
