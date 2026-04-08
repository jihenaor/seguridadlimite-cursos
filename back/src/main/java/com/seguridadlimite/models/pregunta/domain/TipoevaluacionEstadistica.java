package com.seguridadlimite.models.pregunta.domain;

import lombok.Data;

@Data
public class TipoevaluacionEstadistica {
    String criterio;
    String nombreopcion;
    Integer contador;
    double porcentaje;
}
