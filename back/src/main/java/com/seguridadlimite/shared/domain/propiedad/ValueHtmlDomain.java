package com.seguridadlimite.shared.domain.propiedad;

import com.seguridadlimite.shared.domain.exceptions.ExcepcionInformacionInvalida;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Value object padre de todos los value objects con contenido html
 *
 * @author jorge.henao
 * @create 24/11/2021
 **/
public abstract class ValueHtmlDomain implements Serializable {

    private static final String REGEXP = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    private static final Pattern pattern = Pattern.compile(REGEXP, Pattern.CASE_INSENSITIVE);
    @Getter(AccessLevel.NONE)
    private static final long serialVersionUID = 1L;

    @Getter
    protected final String value;

    public ValueHtmlDomain(String value) {
        this.value = value;
        validar(value);
    }

    /**
     *
     * @param value String
     * @throws ExcepcionInformacionInvalida cadena con caracteres no permitidos
     */
    private void validar(String value) {
        if(value!= null && !value.equals("")){
            validarCaracteresRestringidos(value);
        }
    }

    /**
     *
     * @param value String
     * @throws ExcepcionInformacionInvalida cadena con caracteres no permitidos
     */
    private void validarCaracteresRestringidos(String value) {
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            throw new ExcepcionInformacionInvalida("Informacion invalida.");
        }
    }
}
