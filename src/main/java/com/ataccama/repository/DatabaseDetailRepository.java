package com.ataccama.repository;

import com.ataccama.model.DatabaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatabaseDetailRepository extends JpaRepository<DatabaseDetail, String> {

    Optional<DatabaseDetail> findByName(String innerDbName);

}
