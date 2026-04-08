package com.seguridadlimite.shared.domain.propiedad;

public final class LimpiarXSS {

    private LimpiarXSS() {
        // privado
    }
    public static String limpiar(String value) {
/*
        value = ESAPI.encoder()
                .canonicalize(value)
                .replaceAll("\0", "");

        //Convierte a codificaci�n utf-8
        value = value.trim();
        Charset charset = StandardCharsets.ISO_8859_1;
        value = new String(value.getBytes(charset), charset);

        value = StringEscapeUtils.unescapeHtml(value);
        value = StringEscapeUtils.unescapeJavaScript(value);
        value = StringEscapeUtils.unescapeXml(value);
        value = StringEscapeUtils.unescapeJava(value);
        value = StringEscapeUtils.unescapeCsv(value);
        value = ESAPI.encoder().canonicalize(value);

        //Sanea el los valores entrada de html y javascript para protecci�n
        //contra XSS
        value = Jsoup.clean(value, Whitelist.none());
        value = StringEscapeUtils.unescapeHtml(value);
        return value;

 */
        return "";
    }
}
