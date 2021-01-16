package com.ataccama.service;

import com.ataccama.model.ConnectionDto;
import com.ataccama.model.DatabaseDetailDto;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

public interface DatabaseMetadataService {

    DatabaseMetaData getDatabaseDetails(ConnectionDto connectionDto);

}
