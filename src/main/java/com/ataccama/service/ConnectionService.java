package com.ataccama.service;

import com.ataccama.model.DatabaseDetailDto;

import java.sql.Connection;

public interface ConnectionService {

    Connection establishConnection(DatabaseDetailDto connectionDto);
}
