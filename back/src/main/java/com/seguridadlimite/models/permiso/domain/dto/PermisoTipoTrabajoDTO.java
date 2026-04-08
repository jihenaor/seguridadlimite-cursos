package com.seguridadlimite.models.permiso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoTipoTrabajoDTO {
    private Long idPermisoTipoTrabajo;
    private Long idPermiso;
    private Integer idTipoTrabajo;
    private String descripcion;
} 