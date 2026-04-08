package com.seguridadlimite.shared.domain.propiedad;

import com.seguridadlimite.shared.domain.exceptions.ExcepcionInformacionInvalida;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ValueObligatorioObjectDomain extends ValueObjectDomain {
    @Getter
    protected final String value;

    public ValueObligatorioObjectDomain(String value) {
        super(value);
        validarVacio(value);
        this.value = value;
    }
    private void validarVacio(String cadena){
        if(cadena == null || cadena.equals("")) {
            throw new ExcepcionInformacionInvalida("Se presento un error al obtener un dato vacio.");
        }
    }

    public ValueObligatorioObjectDomain() {
        this.value = null;
    }

    protected void validarLongitudTextoDatosNumericos(int longitud) {
        Pattern pat = Pattern.compile(String.format("[0-9]{%s}", longitud));
        Matcher mat = pat.matcher(value);
        if (!mat.matches()) {
            throw new ExcepcionInformacionInvalida("Se presento un error al asignar un dato invalido.");
        }
    }

    protected void validarLongitud(int longitud) {
        Pattern pat = Pattern.compile(String.format("[\\s\\S]{%s}", longitud));
        Matcher mat = pat.matcher(value);
        if (!mat.matches()) {
            throw new ExcepcionInformacionInvalida("Se presento un error al asignar un dato invalido.");
        }
    }
}