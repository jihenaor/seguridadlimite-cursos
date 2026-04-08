package com.seguridadlimite.springboot.backend.apirest.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
