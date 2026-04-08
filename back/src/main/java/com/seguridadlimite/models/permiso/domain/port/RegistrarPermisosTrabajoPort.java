package com.seguridadlimite.models.permiso.domain.port;

import com.seguridadlimite.models.nivel.application.NivelUpdateDTO;

import java.util.List;

public interface RegistrarPermisosTrabajoPort {
    void registrarPermisos(List<NivelUpdateDTO> nivelesDTO);
} 