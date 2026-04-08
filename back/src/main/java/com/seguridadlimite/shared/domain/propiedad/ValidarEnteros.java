package com.seguridadlimite.shared.domain.propiedad;

public final class ValidarEnteros {

    private ValidarEnteros() {
        //privado
    }

    public static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}
