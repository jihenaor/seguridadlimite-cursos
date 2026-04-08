package com.seguridadlimite.models.pregunta.domain;

public enum TipoevaluacionEnum {
    INGRESO("I"),
    TEORICO("T"),
    ENFASIS("E"),
    PRACTICO("P"),
    ENCUESTA("C");

    private final String equivalente;

    TipoevaluacionEnum(String equivalente) {
        this.equivalente = equivalente;
    }

    public String getEquivalente() {
        return equivalente;
    }
}
