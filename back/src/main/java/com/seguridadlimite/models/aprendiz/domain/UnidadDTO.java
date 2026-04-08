package com.seguridadlimite.models.aprendiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnidadDTO {
    private String unidad;
    private AsistenciaDTO asistencia;
}
