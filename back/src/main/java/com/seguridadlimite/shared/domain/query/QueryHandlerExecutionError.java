package com.seguridadlimite.shared.domain.query;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public final class QueryHandlerExecutionError extends RuntimeException {
    public QueryHandlerExecutionError(Throwable cause) {
        super("Se presento un error en la aplicacion al intentar obtener el recurso.");
    }
}
