package com.seguridadlimite.springboot.backend.apirest.exceptions;

public class DataBaseExecutionException extends RuntimeException {
    public DataBaseExecutionException(String message) {
        super(message);
    }

    public DataBaseExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
