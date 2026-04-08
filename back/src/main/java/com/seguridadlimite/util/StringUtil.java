package com.seguridadlimite.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {
    public static String quitarTildes(String texto) {
        if (texto == null) return null;
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        Pattern patron = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return patron.matcher(normalizado).replaceAll("");
    }
}
