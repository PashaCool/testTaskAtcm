package com.ataccama.exception;

import lombok.Getter;

@Getter
public class NotUniqueInnerDbNameException extends RuntimeException{

    private final String dbName;

    public NotUniqueInnerDbNameException(String dbName) {
        super();
        this.dbName = dbName;
    }

}
