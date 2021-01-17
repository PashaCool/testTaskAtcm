package com.ataccama.exception;

import lombok.Getter;

@Getter
public class EstablishConnectionException extends RuntimeException {

    private final String url;

    public EstablishConnectionException(String url) {
        super();
        this.url = url;
    }

}
