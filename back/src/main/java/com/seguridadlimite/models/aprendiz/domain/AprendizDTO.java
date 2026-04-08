package com.seguridadlimite.models.aprendiz.domain;


import com.seguridadlimite.models.nivel.domain.NivelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AprendizDTO {
    private long id;
    private TrabajadorDTO trabajador;
    private String nombrecompleto;
    private String celular;
    private String correoelectronico;
    private Integer idPermiso;
    private String fechainscripcion;
    private boolean asistenciaCompleta;
    private String niveleducativo;
    private String areatrabajo;
    private String cargoactual;
    private String empleador;
    private String arl;
    private NivelDTO nivel;
}
