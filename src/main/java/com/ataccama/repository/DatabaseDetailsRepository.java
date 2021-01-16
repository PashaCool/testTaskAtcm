package com.ataccama.repository;

import com.ataccama.model.DatabaseDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatabaseDetailsRepository extends CrudRepository<DatabaseDetails, String> {

    Optional<DatabaseDetails> findByDatabaseName(String databaseName);

}
