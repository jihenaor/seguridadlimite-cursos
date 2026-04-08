package com.seguridadlimite.models.grupopregunta.domain;

import com.seguridadlimite.models.evaluacion.dominio.EvaluacionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GrupopreguntaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<EvaluacionDTO> evaluacions;
} 