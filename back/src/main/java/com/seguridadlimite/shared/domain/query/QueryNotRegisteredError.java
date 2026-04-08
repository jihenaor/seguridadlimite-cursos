package com.seguridadlimite.shared.domain.query;


public final class QueryNotRegisteredError extends Exception {
    public QueryNotRegisteredError(Class<? extends Query> query) {
        super(String.format("El query <%s> no tiene un query handler asociado", query.toString()));
    }
}
