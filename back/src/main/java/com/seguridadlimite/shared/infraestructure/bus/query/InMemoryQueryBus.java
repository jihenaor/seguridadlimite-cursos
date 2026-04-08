package com.seguridadlimite.shared.infraestructure.bus.query;

import com.seguridadlimite.shared.domain.query.Query;
import com.seguridadlimite.shared.domain.query.QueryBus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public final class InMemoryQueryBus implements QueryBus {
    private final QueryHandlersInformation information;
    private final ApplicationContext context;

    @Override
    public <R> R ask(Query query) {
        return null;
    }
/*
    @Override
    public Response ask(Query query) {
        try {
            Class<? extends QueryHandler
                    > queryHandlerClass = information.search(query.getClass());

            QueryHandler handler = context.getBean(queryHandlerClass);

            return handler.handle(query);
        } catch (DomainError ex) {
            throw ex;
        } catch (Exception error) {
            throw new QueryHandlerExecutionError(error);
        }
    }

 */
}
