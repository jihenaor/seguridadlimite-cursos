package com.seguridadlimite.models.permiso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoDetalleActividadDTO {
    private Integer idPermisoActividad;
    private Integer idPermiso;
    private String actividadrealizar;
    private String peligros;
    private String controlesrequeridos;
} 