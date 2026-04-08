package com.seguridadlimite.models.permiso.domain.port;

import com.seguridadlimite.models.permiso.domain.PermisoFechas;

import java.util.List;

public interface PermisoFechasPort {
    PermisoFechas save(PermisoFechas permisoFechas);
    List<PermisoFechas> saveAll(List<PermisoFechas> permisoFechas);
}
