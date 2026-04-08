package com.seguridadlimite.models.pregunta.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PreguntaDTO {
    private Long id;
    private String pregunta;
    private String respuesta;
    private String tipoevaluacion;
    private Long idgrupo;
} 