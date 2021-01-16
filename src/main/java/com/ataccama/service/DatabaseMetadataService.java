package com.ataccama.service;

import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.model.TableDefinition;

import java.util.List;

public interface DatabaseMetadataService {

    List<TableDefinition> getDatabaseDetails(DatabaseDetailDto connectionDto);

}
