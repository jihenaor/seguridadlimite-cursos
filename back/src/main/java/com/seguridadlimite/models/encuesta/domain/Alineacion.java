package com.seguridadlimite.models.encuesta.domain;

import com.itextpdf.layout.property.TextAlignment;

public enum Alineacion {
    CENTRO(TextAlignment.CENTER);

    private final TextAlignment alignment;

    Alineacion(TextAlignment alignment) {
        this.alignment = alignment;
    }

    public TextAlignment getAlignment() {
        return alignment;
    }
} 