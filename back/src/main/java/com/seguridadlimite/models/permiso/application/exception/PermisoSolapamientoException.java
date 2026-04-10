package com.seguridadlimite.models.permiso.application.exception;

import com.seguridadlimite.models.permiso.application.dto.PermisoSolapamientoConflictoDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class PermisoSolapamientoException extends RuntimeException {

    private final List<PermisoSolapamientoConflictoDTO> conflictos;

    public PermisoSolapamientoException(String message, List<PermisoSolapamientoConflictoDTO> conflictos) {
        super(message);
        this.conflictos = conflictos;
    }
}
