package com.ataccama.controller;

import com.ataccama.model.ConnectionDto;
import com.ataccama.service.DatabaseMetadataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.DatabaseMetaData;

@RestController
@RequestMapping(path = "/api/external", consumes = {"application/json", "text/xml"})
public class ExternalConnectionController {

    private DatabaseMetadataService connector;

    public ExternalConnectionController(DatabaseMetadataService connector) {
        this.connector = connector;
    }

    @PostMapping("/databaseMetaData")
    public DatabaseMetaData connectToDatabase(@RequestBody ConnectionDto connectionDto) {
        return connector.getDatabaseDetails(connectionDto);
    }

}
