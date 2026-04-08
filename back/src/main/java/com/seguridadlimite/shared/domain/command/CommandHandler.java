package com.seguridadlimite.shared.domain.command;


public interface CommandHandler<T extends Command> {
    void handle(T command);
}
