package com.seguridadlimite.models.permiso.application.port.in;

import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;

import java.util.List;

public interface ConsultarPermisosTrabajoAlturasPorFechaUseCase {
    List<PermisoTrabajoAlturas> ejecutar(String fecha);
} 