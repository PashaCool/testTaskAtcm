package com.ataccama.service;

import com.ataccama.model.DatabaseDetail;
import com.ataccama.repository.DatabaseDetailRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseDetailServiceImpl implements DatabaseDetailService {

    private final DatabaseDetailRepository dbDetailRepository;

    public DatabaseDetailServiceImpl(DatabaseDetailRepository dbDetailRepository) {
        this.dbDetailRepository = dbDetailRepository;
    }

    @Override
    public Iterable<DatabaseDetail> findAllDatabases() {
        return dbDetailRepository.findAll();
    }

    @Override
    public DatabaseDetail createNewDbConnection(DatabaseDetail entity) {
        return dbDetailRepository.save(entity);
    }

    @Override
    public void deleteDbConnection(String uuid) {
        dbDetailRepository.deleteById(uuid);
    }

    @Override
    public DatabaseDetail updateDbConnection(DatabaseDetail entity) {
        return dbDetailRepository.save(entity);
    }

    @Override
    public Optional<DatabaseDetail> findById(String uuid) {
        return dbDetailRepository.findById(uuid);
    }

}
