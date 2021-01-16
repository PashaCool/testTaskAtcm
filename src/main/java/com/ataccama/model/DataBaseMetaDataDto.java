package com.ataccama.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataBaseMetaDataDto {

    private List<String> tabaleNames = new ArrayList<>();

    public void addTableName(String name) {
        tabaleNames.add(name);
    }
}
