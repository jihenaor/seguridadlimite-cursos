package com.seguridadlimite.shared.domain.propiedad;

import com.seguridadlimite.shared.domain.exceptions.ExcepcionInformacionInvalida;
import com.seguridadlimite.shared.domain.numero.Numero;

import java.util.regex.Pattern;

public abstract class ValueYearCadenaDomain extends Numero {
    private static final String REGEXP = "^([0-9]){4}$";

    public ValueYearCadenaDomain(String value) {
        super(value);
        validarNumeroCuatroDigitos(value);
    }

    public ValueYearCadenaDomain() {
        super();
    }

    private void validarNumeroCuatroDigitos(String value) {
        if (!Pattern.matches(REGEXP, value)) {
            throw new ExcepcionInformacionInvalida("Solo informacion numerica de cuatro digitos.");
        }
    }

}
