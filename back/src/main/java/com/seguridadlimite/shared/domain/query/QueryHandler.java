package com.seguridadlimite.shared.domain.query;

import java.text.ParseException;


public interface QueryHandler<Q extends Query, R extends Response> {
    R handle(Q query) throws ParseException;
}
