package com.ataccama.service;

import com.ataccama.model.ConnectionDto;

import java.sql.Connection;

public interface ConnectionService {

    Connection establishConnection(ConnectionDto connectionDto);
}
