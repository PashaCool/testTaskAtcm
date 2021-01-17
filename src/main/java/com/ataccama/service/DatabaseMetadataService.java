package com.ataccama.service;

import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.model.QueryRequest;
import com.ataccama.model.TableDefinition;

import java.util.List;
import java.util.Map;

public interface DatabaseMetadataService {

    List<TableDefinition> getDatabaseDetails(DatabaseDetailDto connectionDto);

    Map<String, Object> executeQuery(QueryRequest request);
}
