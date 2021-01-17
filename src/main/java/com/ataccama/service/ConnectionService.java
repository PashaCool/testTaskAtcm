package com.ataccama.service;

import com.ataccama.model.DatabaseDetailDto;

import javax.sql.DataSource;

public interface ConnectionService {

    DataSource establishConnection(DatabaseDetailDto connectionDto);
}
