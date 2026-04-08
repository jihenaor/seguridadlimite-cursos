package com.seguridadlimite.shared.domain.exceptions;

import lombok.Getter;

@Getter
public abstract class DomainError extends RuntimeException {

    private final String errorMessage;

    public DomainError(String errorMessage) {
        super(errorMessage);

        this.errorMessage = errorMessage;
    }

}
