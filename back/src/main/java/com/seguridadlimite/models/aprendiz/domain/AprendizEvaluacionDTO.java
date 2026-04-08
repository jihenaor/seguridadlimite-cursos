package com.seguridadlimite.models.aprendiz.domain;

import com.seguridadlimite.models.nivel.domain.NivelDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AprendizEvaluacionDTO {
    private Long id;
    private Long idtrabajador;
    private String numerodocumento;
    private String nombreCompletoTrabajador;
    private NivelDTO nivel;
    private String fechaencuesta;
    private Integer eingreso;      // Evaluación de conocimiento
    private Integer eteorica1;     // Evaluación teórica 1
    private Integer eteorica2;     // Evaluación teórica 2
    private Integer epractica;     // Evaluación práctica
    private Boolean asistio;
    private Integer calificacion;
    private Date fechaEvaluacion;
    private Boolean evaluacionCompletada;
    private String pagocurso;
    private String fechainscripcion;
    private String foto;
    private String fechaemision;
    private String fechareentrenamiento;
    private String codigoverificacion;
    private List<ModuloDTO> modulos;
    private long idpermiso;
    private String codigoministerio;
} 