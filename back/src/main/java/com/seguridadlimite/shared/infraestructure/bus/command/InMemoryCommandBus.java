package com.seguridadlimite.shared.infraestructure.bus.command;

import com.seguridadlimite.shared.domain.command.Command;
import com.seguridadlimite.shared.domain.command.CommandBus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public final class InMemoryCommandBus implements CommandBus {
    @Override
    public void dispatch(Command command) {

    }
/*
    private final CommandHandlersInformation information;
    private final ApplicationContext context;


    @Override
    public void dispatch(Command command) {
        try {
            Class<? extends CommandHandler> commandHandlerClass = information.search(command.getClass());

            CommandHandler handler = context.getBean(commandHandlerClass);

            handler.handle(command);
        } catch (DomainError ex) {
            throw ex;
        } catch (Exception error) {
            throw new CommandHandlerExecutionError(error);
        }
    }

 */
}
