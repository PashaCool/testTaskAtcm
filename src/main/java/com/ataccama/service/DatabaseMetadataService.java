package com.ataccama.service;

import com.ataccama.model.DataBaseMetaDataDto;
import com.ataccama.model.DatabaseDetailDto;

public interface DatabaseMetadataService {

    DataBaseMetaDataDto getDatabaseDetails(DatabaseDetailDto connectionDto);

}
