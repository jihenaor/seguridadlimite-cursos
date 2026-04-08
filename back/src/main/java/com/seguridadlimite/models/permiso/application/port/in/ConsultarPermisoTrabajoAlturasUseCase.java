package com.seguridadlimite.models.permiso.application.port.in;

import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;

import java.util.List;
import java.util.Optional;

public interface ConsultarPermisoTrabajoAlturasUseCase {
    PermisoTrabajoAlturas consultar(Integer idPermiso);
    Optional<PermisoTrabajoAlturas> consultarCodigoministerio(String codigoministerio);
    List<PermisoTrabajoAlturas> findAll();
} 