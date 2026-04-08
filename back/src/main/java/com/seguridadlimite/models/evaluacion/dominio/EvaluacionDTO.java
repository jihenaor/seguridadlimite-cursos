package com.seguridadlimite.models.evaluacion.dominio;

import com.seguridadlimite.models.pregunta.domain.PreguntaDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EvaluacionDTO {
    private Long id;
    private Long idaprendiz;
    private Long idpregunta;
    private String respuestacorrecta;
    private Integer numero;
    private PreguntaDTO pregunta;
} 