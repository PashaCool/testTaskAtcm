package com.ataccama.model;

import lombok.Data;

import java.util.List;

@Data
public class TableDefinition {

    private String tableName;
    private List<ColumnDefinition> columnDefinitions;

    public TableDefinition(String tableName, List<ColumnDefinition> columnDefinitions) {
        this.tableName = tableName;
        this.columnDefinitions = columnDefinitions;
    }

}
