package com.seguridadlimite.models.permiso.application.port.out;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;

import java.util.List;

public interface ConsultarDatosMinisterioPort {
    
    /**
     * Busca permisos de trabajo por lista de IDs
     */
    List<PermisoTrabajoAlturas> buscarPermisosPorIds(List<Integer> idPermisosTrabajos);
    
    /**
     * Busca aprendices asociados a un permiso de trabajo específico
     */
    List<Aprendiz> buscarAprendicesPorIdPermiso(Integer idPermiso);
}
