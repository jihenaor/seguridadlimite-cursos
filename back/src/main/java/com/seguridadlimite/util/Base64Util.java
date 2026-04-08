package com.seguridadlimite.util;

public class Base64Util {
    public static String extractBase64String(String dataUrl) {

        if (dataUrl.startsWith("data:image") && dataUrl.contains(";base64,")) {

            String[] parts = dataUrl.split(";base64,");
            if (parts.length == 2) {
                return parts[1];
            }
        }


        return null;
    }
}
