package com.ataccama;

import com.ataccama.service.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApplicationRunner {

    @Autowired
    private Connector connector;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

    @GetMapping("/api/smth")
    public void smth() {
        connector.smth();
    }

}
