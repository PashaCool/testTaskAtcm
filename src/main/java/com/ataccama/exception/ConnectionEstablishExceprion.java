package com.ataccama.exception;

public class ConnectionEstablishExceprion extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Unable obtain connection to database %s with username %s";
    private final String dbUrl;
    private final String userName;

    public ConnectionEstablishExceprion(String dbUrl, String userName) {
        super();
        this.dbUrl = dbUrl;
        this.userName = userName;
    }

    @Override
    public String getMessage() {
        return String.format(MESSAGE_TEMPLATE, dbUrl, userName);
    }

}
