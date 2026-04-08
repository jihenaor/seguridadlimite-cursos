package com.seguridadlimite.springboot.backend.apirest.util;

public class UtilidadNumero {
    public static boolean esNumero(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


