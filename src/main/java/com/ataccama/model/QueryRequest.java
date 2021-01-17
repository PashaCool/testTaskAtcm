package com.ataccama.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QueryRequest {

    private DatabaseDetailDto connectionDefinition;
    private String dataSource; //table, view
    private String columns;

}
