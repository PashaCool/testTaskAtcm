package com.ataccama.service;

import com.ataccama.model.DatabaseDetail;
import com.ataccama.model.DatabaseDetailDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DatabaseDetailMapper {

    public DatabaseDetailDto map(DatabaseDetail entity) {
        return DatabaseDetailDto.builder()
                                .uuid(entity.getUuid())
                                .name(entity.getName())
                                .hostName(entity.getHostName())
                                .port(entity.getPort())
                                .databaseName(entity.getDatabaseName())
                                .userName(entity.getUserName())
                                .password(entity.getPassword())
                                .build();
    }

    public DatabaseDetail map(DatabaseDetailDto dto) {
        DatabaseDetail databaseDetail = new DatabaseDetail();
        databaseDetail.setUuid(dto.getUuid());
        databaseDetail.setName(dto.getName());
        databaseDetail.setHostName(dto.getHostName());
        databaseDetail.setPort(dto.getPort());
        databaseDetail.setDatabaseName(dto.getDatabaseName());
        databaseDetail.setUserName(dto.getUserName());
        databaseDetail.setPassword(dto.getPassword());
        return databaseDetail;
    }

    public List<DatabaseDetailDto> map(Iterable<DatabaseDetail> entities) {
        if (entities.iterator().hasNext()) {
            List<DatabaseDetailDto> databaseDetailDtoList = new ArrayList<>();
            entities.forEach(entity -> databaseDetailDtoList.add(this.map(entity)));
            return databaseDetailDtoList;
        } else {
            return Collections.emptyList();
        }
    }

}
