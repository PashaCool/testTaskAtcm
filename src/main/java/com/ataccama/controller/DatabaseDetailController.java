package com.ataccama.controller;

import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.service.DatabaseDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/connection", produces = {"application/json", "text/xml"})
public class DatabaseDetailController {

    private final DatabaseDetailService dbDetailService;

    /**
     * Show all saved connections
     * @return list of saved connections: uuid, inner name, host, port, database name, user and password
     */
    @GetMapping("/all")
    public List<DatabaseDetailDto> findAllDbConnections() {
        return dbDetailService.findAllDatabases();
    }

    /**
     * Save connection
     * @param databaseDetailDto apply new connection details: inner name, host, port, database name, userName and password
     * @return return uuid of created entity
     */
    @PostMapping(path = "/create", consumes = "application/json")
    public ResponseEntity<String> createConnection(@RequestBody DatabaseDetailDto databaseDetailDto) {
        String uuid = dbDetailService.createDbConnection(databaseDetailDto);
        if (!uuid.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Delete record by uuid
     * @param uuid uuid of record
     * @return response status
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteConnection(@PathVariable String uuid) {
        dbDetailService.deleteDbConnection(uuid);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update table by matching uuid
     * @param databaseDetailDto New version of record. If uuid match - update record, otherwise - create new record
     * @return - updated record
     */
    @RequestMapping(value = "/update", method = {RequestMethod.PATCH, RequestMethod.PUT}, consumes = "application/json")
    public ResponseEntity<DatabaseDetailDto> update(@RequestBody DatabaseDetailDto databaseDetailDto) {
        DatabaseDetailDto databaseDetail = dbDetailService.updateDbConnection(databaseDetailDto);
        return ResponseEntity.ok(databaseDetail);
    }

    /**
     * Get connection details by uuid
     * @param uuid uuid of required connection
     * @return connection definition: inner name, host, port, database name, userName and password
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<DatabaseDetailDto> findDBConnectionById(@PathVariable String uuid) {
        return dbDetailService.findById(uuid)
                              .map(ResponseEntity::ok)
                              .orElse(ResponseEntity.noContent().build());
    }

}
