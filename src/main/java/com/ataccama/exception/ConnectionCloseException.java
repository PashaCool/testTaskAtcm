package com.ataccama.exception;

public class ConnectionCloseException extends RuntimeException{

    private static final String REASON = "Error occurred when connection had closed";

    public ConnectionCloseException() {
        super();
    }

    @Override
    public String getMessage() {
        return REASON;
    }

}
