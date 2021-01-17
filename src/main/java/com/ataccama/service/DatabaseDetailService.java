package com.ataccama.service;

import com.ataccama.model.DatabaseDetailDto;

import java.util.List;
import java.util.Optional;

public interface DatabaseDetailService {

    List<DatabaseDetailDto> findAllDatabases();

    String createDbConnection(DatabaseDetailDto entity);

    void deleteDbConnection(String uuid);

    DatabaseDetailDto updateDbConnection(DatabaseDetailDto entity);

    Optional<DatabaseDetailDto> findById(String uuid);

}
