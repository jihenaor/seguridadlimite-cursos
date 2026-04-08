package com.seguridadlimite.models.permiso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleChequeoDTO {
    private Integer idDetalle;
    private Integer idGrupo;
    private String descripcion;
    private String estado;
    private String respuesta;
} 