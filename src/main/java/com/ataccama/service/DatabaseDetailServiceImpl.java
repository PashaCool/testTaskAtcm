package com.ataccama.service;

import com.ataccama.model.DatabaseDetail;
import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.repository.DatabaseDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseDetailServiceImpl implements DatabaseDetailService {

    private final DatabaseDetailRepository dbDetailRepository;
    private final DatabaseDetailMapper databaseDetailMapper;

    public DatabaseDetailServiceImpl(DatabaseDetailRepository dbDetailRepository, DatabaseDetailMapper databaseDetailMapper) {
        this.dbDetailRepository = dbDetailRepository;
        this.databaseDetailMapper = databaseDetailMapper;
    }

    @Override
    public List<DatabaseDetailDto> findAllDatabases() {
        Iterable<DatabaseDetail> dbConnections = dbDetailRepository.findAll();
        return databaseDetailMapper.map(dbConnections);
    }

    @Override
    public DatabaseDetailDto createNewDbConnection(DatabaseDetailDto dto) {
        DatabaseDetail entity = databaseDetailMapper.map(dto);
        DatabaseDetail save = dbDetailRepository.save(entity);
        return databaseDetailMapper.map(save);
    }

    @Override
    public void deleteDbConnection(String uuid) {
        dbDetailRepository.deleteById(uuid);
    }

    @Override
    public DatabaseDetailDto updateDbConnection(DatabaseDetailDto dto) {
        return this.createNewDbConnection(dto);
    }

    @Override
    public Optional<DatabaseDetailDto> findById(String uuid) {
        Optional<DatabaseDetail> byId = dbDetailRepository.findById(uuid);
        return byId.map(databaseDetailMapper::map);
    }

}
