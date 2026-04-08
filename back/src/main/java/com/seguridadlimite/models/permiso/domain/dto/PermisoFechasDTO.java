package com.seguridadlimite.models.permiso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoFechasDTO {
    private Integer id;
    private Integer idPermiso;
    private String fecha;
    private int dia;
}
