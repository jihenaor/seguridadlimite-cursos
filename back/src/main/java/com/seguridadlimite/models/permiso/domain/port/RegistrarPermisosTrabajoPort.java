package com.seguridadlimite.models.permiso.domain.port;

import com.seguridadlimite.models.nivel.application.NivelUpdateDTO;

import java.util.List;

public interface RegistrarPermisosTrabajoPort {
    /**
     * @param forzarSolapamiento si es {@code true}, se crean permisos nuevos aunque exista solapamiento
     *                           con otro permiso del mismo nivel; si es {@code false} y hay solapamiento,
     *                           se lanza {@link com.seguridadlimite.models.permiso.application.exception.PermisoSolapamientoException}.
     */
    void registrarPermisos(List<NivelUpdateDTO> nivelesDTO, boolean forzarSolapamiento);
} 