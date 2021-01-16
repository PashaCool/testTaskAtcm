package com.ataccama.service;

import com.ataccama.model.DatabaseDetail;

import java.util.Optional;

public interface DatabaseDetailService {

    Iterable<DatabaseDetail> findAllDatabases();

    DatabaseDetail createNewDbConnection(DatabaseDetail entity);

    void deleteDbConnection(String uuid);

    DatabaseDetail updateDbConnection(DatabaseDetail entity);

    Optional<DatabaseDetail> findById(String uuid);

}
