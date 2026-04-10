package com.seguridadlimite.models.permiso.application.dto;

import java.util.List;

/**
 * Cuerpo JSON ante HTTP 409 al detectar solapamiento de permisos por nivel y fechas.
 */
public record PermisoSolapamientoResponse(
        String code,
        String message,
        List<PermisoSolapamientoConflictoDTO> conflictos
) {
    public static PermisoSolapamientoResponse of(String message, List<PermisoSolapamientoConflictoDTO> conflictos) {
        return new PermisoSolapamientoResponse("PERMISO_SOLAPAMIENTO", message, conflictos);
    }
}
