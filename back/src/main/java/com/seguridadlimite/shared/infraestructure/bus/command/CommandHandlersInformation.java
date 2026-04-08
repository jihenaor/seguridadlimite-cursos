package com.seguridadlimite.shared.infraestructure.bus.command;


import org.springframework.stereotype.Service;


@Service
public final class CommandHandlersInformation {
/*
    HashMap<Class<? extends Command>, Class<? extends CommandHandler>> indexedCommandHandlers;

    public CommandHandlersInformation() {
        Reflections                          reflections = new Reflections("com.sura");
        Set<Class<? extends CommandHandler>> classes     = reflections.getSubTypesOf(CommandHandler.class);

        indexedCommandHandlers = formatHandlers(classes);
    }

    public Class<? extends CommandHandler> search(Class<? extends Command> commandClass) throws CommandNotRegisteredError {
        Class<? extends CommandHandler> commandHandlerClass = indexedCommandHandlers.get(commandClass);

        if (null == commandHandlerClass) {
            throw new CommandNotRegisteredError(commandClass);
        }

        return commandHandlerClass;
    }

    private HashMap<Class<? extends Command>, Class<? extends CommandHandler>> formatHandlers(
            Set<Class<? extends CommandHandler>> commandHandlers
    ) {
        HashMap<Class<? extends Command>, Class<? extends CommandHandler>> handlers = new HashMap<>();

        for (Class<? extends CommandHandler> handler : commandHandlers) {
            ParameterizedType paramType    = (ParameterizedType) handler.getGenericInterfaces()[0];
            Class<? extends Command> commandClass = (Class<? extends Command>) paramType.getActualTypeArguments()[0];

            handlers.put(commandClass, handler);
        }

        return handlers;
    }

 */
}
