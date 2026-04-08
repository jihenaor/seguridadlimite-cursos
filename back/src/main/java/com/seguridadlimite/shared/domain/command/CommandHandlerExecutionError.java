package com.seguridadlimite.shared.domain.command;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public final class CommandHandlerExecutionError extends RuntimeException {
    public CommandHandlerExecutionError(Throwable cause) {
        super("Se presento un error en la aplicacion al intentar procesar la peticion.");
    }
}
