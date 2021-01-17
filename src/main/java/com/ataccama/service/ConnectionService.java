package com.ataccama.service;

import com.ataccama.model.DatabaseDetailDto;

import javax.sql.DataSource;

public interface ConnectionService {

    /**
     * Obtain connection
     * @param connectionDto - connection definition: inner name, host, port, database name, userName and password
     * @return data source
     */
    DataSource establishConnection(DatabaseDetailDto connectionDto);

    /**
     * Build connection url to for connecting
     * @param connectionDto - connection definition: inner name, host, port, database name
     * @return connection url
     */
    String accumulateUrl(DatabaseDetailDto connectionDto);
}
