package com.seguridadlimite.models.permiso.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformeministerioDto {
    
    private Long id;
    
    // Entrenador fields
    private Long entrenadorId;
    
    private String entrenadorNombrecompleto;
    
    // Supervisor fields
    private Long supervisorId;
    
    private String supervisorNombrecompleto;
    
    // Group fields
    private String fechainicio;
    
    private String fechafinal;
    
    private Integer cupoinicial;
    
    private Integer cupofinal;
    
    private String codigoministerio;
    
    // Aprendiz fields
    private Long aprendizId;
    
    private String aprendizCodigoverificacion;
    
    // Trabajador fields
    private Long trabajadorId;
    
    private String trabajadorNumerodocumento;
    
    private String trabajadorNombrecompleto;
    
    private String trabajadorAreatrabajo;
    
    private String trabajadorNiveleducativo;
    
    private String trabajadorCargoactual;
    
    private String trabajadorGenero;
    
    private String trabajadorNacionalidad;
}
