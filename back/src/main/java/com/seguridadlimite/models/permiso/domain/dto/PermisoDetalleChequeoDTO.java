package com.seguridadlimite.models.permiso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoDetalleChequeoDTO {
    private Integer idPermisoDetalle;
    private int idPermiso;
    private Integer idGrupo;
    private String descripcion;
    private String respuesta;
}