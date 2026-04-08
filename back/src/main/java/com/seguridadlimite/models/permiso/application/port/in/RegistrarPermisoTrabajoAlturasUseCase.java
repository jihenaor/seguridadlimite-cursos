package com.seguridadlimite.models.permiso.application.port.in;

import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;

public interface RegistrarPermisoTrabajoAlturasUseCase {
    PermisoTrabajoAlturas ejecutar(PermisoTrabajoAlturas permisoTrabajoAlturas);
} 