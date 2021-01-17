package com.ataccama.controller;

import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.model.QueryRequest;
import com.ataccama.model.TableDefinition;
import com.ataccama.service.DatabaseMetadataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/external")
public class ExternalConnectionController {

    private final DatabaseMetadataService metadataService;

    public ExternalConnectionController(DatabaseMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @PostMapping(path = "/databaseMetaData", consumes = {"application/json", "text/xml"}, produces = {"application/json", "text/xml"})
    public List<TableDefinition> databaseMetaData(@RequestBody DatabaseDetailDto connectionDto) {
        return metadataService.getDatabaseDetails(connectionDto);
    }

    @PostMapping(path = "/query", consumes = {"application/json", "text/xml"}, produces = {"application/json", "text/xml"})
    public  Map<String, Object> executeQuery(@RequestBody QueryRequest request) {
        return metadataService.executeQuery(request);
    }

}
