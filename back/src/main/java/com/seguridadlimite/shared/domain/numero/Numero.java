package com.seguridadlimite.shared.domain.numero;

import com.seguridadlimite.shared.domain.exceptions.ExcepcionInformacionInvalida;
import com.seguridadlimite.shared.domain.propiedad.ValueObjectDomain;
import lombok.Getter;

import java.util.regex.Pattern;


public abstract class Numero extends ValueObjectDomain {

    private static final String REGEXP = "^\\d*$";

    @Getter
    protected final String value;

    public Numero(String value) {
        super(value);
        validarNumero(value);
        this.value = value;
    }

    public Numero() {
        super();
        this.value = null;
    }

    private void validarNumero(String value) {
        if (!Pattern.matches(REGEXP, value)) {
            throw new ExcepcionInformacionInvalida("Solo informacion numerica.");
        }
    }
}
