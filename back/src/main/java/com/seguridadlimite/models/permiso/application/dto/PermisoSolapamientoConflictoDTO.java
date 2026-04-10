package com.seguridadlimite.models.permiso.application.dto;

/**
 * Detalle de un permiso ya existente que se solapa con el rango solicitado para el mismo nivel.
 */
public record PermisoSolapamientoConflictoDTO(
        Integer idNivel,
        Integer idPermisoExistente,
        String permisoExistenteValidoDesde,
        String permisoExistenteValidoHasta,
        String solicitudValidoDesde,
        String solicitudValidoHasta
) {}
