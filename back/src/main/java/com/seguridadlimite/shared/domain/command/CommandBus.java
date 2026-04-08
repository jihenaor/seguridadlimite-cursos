package com.seguridadlimite.shared.domain.command;


public interface CommandBus {
    /**
     *
     * @param command
     * @throws CommandHandlerExecutionError
     */
    void dispatch(Command command);
}
