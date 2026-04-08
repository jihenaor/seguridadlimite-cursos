package com.seguridadlimite.models.permiso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrupoChequeoDTO {
    private Integer idGrupo;
    private String descripcion;
    private String estado;
    private List<DetalleChequeoDTO> detalles;
} 