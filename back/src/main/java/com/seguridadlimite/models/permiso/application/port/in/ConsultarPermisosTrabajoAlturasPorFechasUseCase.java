package com.seguridadlimite.models.permiso.application.port.in;

import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;

import java.util.List;

public interface ConsultarPermisosTrabajoAlturasPorFechasUseCase {
    List<PermisoTrabajoAlturas> ejecutar(String fechaInicio, String fechaFin);

    List<PermisoTrabajoAlturas> ejecutarPorCodigoMinisterio(String codigoministerio);
} 