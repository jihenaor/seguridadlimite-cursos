package com.seguridadlimite.shared.domain.propiedad;


import com.seguridadlimite.shared.domain.exceptions.ExcepcionInformacionInvalida;
import com.seguridadlimite.util.ValidarPropiedad;

import java.util.regex.Pattern;

public abstract class DoubleTextoDomain extends ValueObjectDomain {

    private static final String REGEXP_DOUBLE = "^(\\d*\\.?\\d+|\\d{1,3}(,\\d{3})*(\\.\\d+)?)$";

    public DoubleTextoDomain(String value) {
        super(value);
        validarCaracteresRestringidos(value);
    }
    public DoubleTextoDomain(){
        super();
    }

    public double obtenerValorMontoEmbargo() {
        return ValidarPropiedad.validarCadena.test(value) ? Double.parseDouble(value) : 0;
    }

    /**
     *
     * @param value String
     * @return String
     * @throws ExcepcionInformacionInvalida cadena con caracteres no permitidos
     */
    private String validarCaracteresRestringidos(String value) {
        if (value != null && value.length() > 0 &&  !Pattern.matches(REGEXP_DOUBLE, value)) {
            throw new ExcepcionInformacionInvalida("Informacion invalida.");
        }
         return value;
    }
}
