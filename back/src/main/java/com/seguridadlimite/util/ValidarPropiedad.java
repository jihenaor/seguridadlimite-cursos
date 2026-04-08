package com.seguridadlimite.util;

import com.seguridadlimite.shared.domain.propiedad.ValueObjectDomain;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ValidarPropiedad {

    private ValidarPropiedad(){
        // privado
    }

    public static final Function<Object, Boolean> validarNulo = Objects::nonNull;

    public static final Predicate<String> validarCadena = x -> validarNulo.apply(x) && !x.equals("");

    public static final Predicate<ValueObjectDomain> validarValueObjectCadena = x -> validarNulo.apply(x) && validarNulo.apply(x.getValue()) && !x.getValue().equals("");

}
