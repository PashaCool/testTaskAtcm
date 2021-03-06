package com.ataccama.service;

import com.ataccama.exception.NotUniqueInnerDbNameException;
import com.ataccama.model.DatabaseDetail;
import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.repository.DatabaseDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DatabaseDetailServiceImpl implements DatabaseDetailService {

    private final DatabaseDetailRepository dbDetailRepository;
    private final DatabaseDetailMapper databaseDetailMapper;

    @Override
    public List<DatabaseDetailDto> findAllDatabases() {
        Iterable<DatabaseDetail> dbConnections = dbDetailRepository.findAll();
        return databaseDetailMapper.map(dbConnections);
    }

    @Override
    public String createDbConnection(DatabaseDetailDto dto) {
        DatabaseDetail predicate = new DatabaseDetail();
        predicate.setName(dto.getName());
        final boolean exists = dbDetailRepository.exists(Example.of(predicate));
        if (!exists) {
            DatabaseDetail entity = databaseDetailMapper.map(dto);
            DatabaseDetail saved = dbDetailRepository.save(entity);
            return saved.getUuid();
        }
        throw new NotUniqueInnerDbNameException(dto.getName());
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
