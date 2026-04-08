package com.seguridadlimite.shared.domain.propiedad;

import com.seguridadlimite.shared.domain.exceptions.ExcepcionInformacionInvalida;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Value object padre de todos los value objects tipo string
 *
 * @author jorge.henao
 * @create 01/08/2022
 **/
public abstract class ValueCorreoDomain implements Serializable {

    private static final String REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(REGEXP, Pattern.CASE_INSENSITIVE);
    @Getter(AccessLevel.NONE)
    private static final long serialVersionUID = 1L;

    @Getter
    protected final String value;

    public ValueCorreoDomain(String value) {
        this.value = validar(value);
    }

    public ValueCorreoDomain() {
        this.value = null;
    }

    /**
     *
     * @param value String
     * @return String
     * @throws ExcepcionInformacionInvalida cadena con caracteres no permitidos
     */
    private String validar(String value) {
        String resultado;
        if(value!= null && !value.trim().equals("")){
            resultado = validarFormato(value);
        }else {
            resultado = value;
        }
        return resultado;
    }

    /**
     *
     * @param value String
     * @return String
     * @throws ExcepcionInformacionInvalida cadena con caracteres no permitidos
     */
    private String validarFormato(String value) {
        if (!pattern.matcher(value).find()) {
            throw new ExcepcionInformacionInvalida("El correo electronico digitado no es valido");
        }

        return LimpiarXSS.limpiar(value);
    }
}
