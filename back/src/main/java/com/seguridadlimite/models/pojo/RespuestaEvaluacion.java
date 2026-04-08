package com.seguridadlimite.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RespuestaEvaluacion {
    private double notaEvaluacionTeorica;
    private double notaEnfasis;
    private int numeroPreguntasAprobadas;
    private int numeroPreguntasActualizadas;
}
