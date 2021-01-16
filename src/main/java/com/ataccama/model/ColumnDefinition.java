package com.ataccama.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ColumnDefinition {

    private String columnName;
    private String columnType;
    private boolean isNullable;
    private boolean isAutoIncrement;
    private String columnSize;

    public ColumnDefinition(String columnName, String columnType, boolean isNullable, boolean isAutoIncrement) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.isNullable = isNullable;
        this.isAutoIncrement = isAutoIncrement;
    }

}
