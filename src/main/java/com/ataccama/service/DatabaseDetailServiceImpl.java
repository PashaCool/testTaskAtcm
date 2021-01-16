package com.ataccama.service;

import com.ataccama.model.DatabaseDetail;
import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.repository.DatabaseDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseDetailServiceImpl implements DatabaseDetailService {

    private static final String EMPTY_STRING = "";
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
    public String createNewDbConnection(DatabaseDetailDto dto) {
        Optional<DatabaseDetail> byDatabaseName = dbDetailRepository.findByDatabaseName(dto.getName());
        if (byDatabaseName.isEmpty()) {
            DatabaseDetail entity = databaseDetailMapper.map(dto);
            DatabaseDetail saved = dbDetailRepository.save(entity);
            return saved.getUuid();
        }
        return EMPTY_STRING;
    }

    @Override
    public void deleteDbConnection(String uuid) {
        dbDetailRepository.deleteById(uuid);
    }

    @Override
    public DatabaseDetailDto updateDbConnection(DatabaseDetailDto dto) {
        DatabaseDetail request = databaseDetailMapper.map(dto);
        DatabaseDetail updatedEntity = this.dbDetailRepository.save(request);
        return databaseDetailMapper.map(updatedEntity);
    }

    @Override
    public Optional<DatabaseDetailDto> findById(String uuid) {
        Optional<DatabaseDetail> byId = dbDetailRepository.findById(uuid);
        return byId.map(databaseDetailMapper::map);
    }

}
