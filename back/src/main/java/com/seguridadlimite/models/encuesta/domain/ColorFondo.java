package com.seguridadlimite.models.encuesta.domain;

public enum ColorFondo {
    AMARILLO(new com.itextpdf.kernel.colors.DeviceRgb(255, 255, 200)),
    GRIS(new com.itextpdf.kernel.colors.DeviceRgb(220, 220, 220));

    private final com.itextpdf.kernel.colors.Color color;

    ColorFondo(com.itextpdf.kernel.colors.Color color) {
        this.color = color;
    }

    public com.itextpdf.kernel.colors.Color getColor() {
        return color;
    }
} 