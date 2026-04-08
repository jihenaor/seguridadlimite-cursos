package com.seguridadlimite.shared.controller;


import com.seguridadlimite.shared.domain.command.Command;
import com.seguridadlimite.shared.domain.command.CommandBus;
import com.seguridadlimite.shared.domain.query.Query;
import com.seguridadlimite.shared.domain.query.QueryBus;

public abstract class ApiController {
    protected final QueryBus queryBus;
    protected final CommandBus commandBus;


    public ApiController(QueryBus queryBus, CommandBus commandBus) {
        this.queryBus   = queryBus;
        this.commandBus = commandBus;
    }
    
    /**
     *
     * @param command
     * @throws com.seguridadlimite.shared.domain.command.CommandHandlerExecutionError
     *
     *
     */
    protected void dispatch(Command command)  {
        commandBus.dispatch(command);
    }

    /**
     *
     * @param query
     * @param <R>
     * @return
     * @throws QueryHandlerExecutionError
     */
    protected <R> R ask(Query query) {
        return queryBus.ask(query);
    }

}
