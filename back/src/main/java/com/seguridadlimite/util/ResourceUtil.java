package com.seguridadlimite.util;

import java.net.URL;

public class ResourceUtil {

    public static String obtenerRutaArchivo(String nombreArchivo) {
        URL resourceUrl = ResourceUtil.class.getClassLoader().getResource(nombreArchivo);

        if (resourceUrl == null) {
            throw new IllegalArgumentException("El archivo requerido no existe: " + nombreArchivo);
        }

        return resourceUrl.getPath();
    }
}
