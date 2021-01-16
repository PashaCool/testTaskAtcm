package com.ataccama.controller;

import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.service.DatabaseDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = {"application/json", "text/xml"})
public class DatabaseDetailController {

    private final DatabaseDetailService dbDetailService;

    public DatabaseDetailController(DatabaseDetailService dbDetailService) {
        this.dbDetailService = dbDetailService;
    }

    @GetMapping("/allConnections")
    public List<DatabaseDetailDto> findAllDbConnections() {
        return dbDetailService.findAllDatabases();
    }

    @PostMapping(path = "/create", consumes = "application/json")
    public ResponseEntity<DatabaseDetailDto> createConnection(@RequestBody DatabaseDetailDto databaseDetailDto) {
        DatabaseDetailDto newDbConnection = dbDetailService.createNewDbConnection(databaseDetailDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDbConnection);
    }

    @DeleteMapping("/connection/{uuid}")
    public ResponseEntity<?> deleteConnection(@PathVariable String uuid) {
        dbDetailService.deleteDbConnection(uuid);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/updateConnection", method = {RequestMethod.PATCH, RequestMethod.PUT}, consumes = "application/json")
    public ResponseEntity<DatabaseDetailDto> update(@RequestBody DatabaseDetailDto databaseDetailDto) {
        DatabaseDetailDto databaseDetail = dbDetailService.updateDbConnection(databaseDetailDto);
        return ResponseEntity.ok(databaseDetail);
    }

    @GetMapping("/connection/{uuid}")
    public ResponseEntity<DatabaseDetailDto> findDBConnectionById(@PathVariable String uuid) {
        return dbDetailService.findById(uuid)
                              .map(ResponseEntity::ok)
                              .orElse(ResponseEntity.noContent().build());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleException(Exception exception) {
        return exception.getMessage();
    }

}
