package com.seguridadlimite.shared.domain.command;


public final class CommandNotRegisteredError extends Exception {
    public CommandNotRegisteredError(Class<? extends Command> command) {
        super(String.format("El command <%s> no tiene un command handler asociado", command.toString()));
    }
}
