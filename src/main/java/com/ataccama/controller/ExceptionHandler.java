package com.ataccama.controller;

import com.ataccama.exception.EstablishConnectionException;
import com.ataccama.exception.NotUniqueInnerDbNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_OCCURRED = "Error occurred";
    private static final String NOT_UNIQUE_NAME = "Database with name %s exists";
    private static final String ERROR_CONNECTION = "A connection error occurred via %s";

    /**
     * Handle not unique db name at application list
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = NotUniqueInnerDbNameException.class)
    protected ResponseEntity<Object> handleConflict(NotUniqueInnerDbNameException ex) {
        final String message = String.format(NOT_UNIQUE_NAME, ex.getDbName());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle when failed to connect
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = EstablishConnectionException.class)
    protected ResponseEntity<Object> handleConflict(EstablishConnectionException ex) {
        final String message = String.format(ERROR_CONNECTION, ex.getUrl());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Stub handler for hide stacktrace from user
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleConflict() {
        return new ResponseEntity<>(ERROR_OCCURRED, HttpStatus.CONFLICT);
    }
}
