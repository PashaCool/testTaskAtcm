package com.ataccama.controller;

import com.ataccama.model.DataBaseMetaDataDto;
import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.service.DatabaseMetadataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/external", consumes = {"application/json", "text/xml"}, produces = {"application/json", "text/xml"})
public class ExternalConnectionController {

    private final DatabaseMetadataService metadataService;

    public ExternalConnectionController(DatabaseMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @PostMapping("/databaseMetaData")
    public DataBaseMetaDataDto databaseMetaData(@RequestBody DatabaseDetailDto connectionDto) {
        return metadataService.getDatabaseDetails(connectionDto);
    }

}
