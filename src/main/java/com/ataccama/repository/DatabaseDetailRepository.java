package com.ataccama.repository;

import com.ataccama.model.DatabaseDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatabaseDetailRepository extends CrudRepository<DatabaseDetail, String> {

    Optional<DatabaseDetail> findByName(String innerDbName);

}
