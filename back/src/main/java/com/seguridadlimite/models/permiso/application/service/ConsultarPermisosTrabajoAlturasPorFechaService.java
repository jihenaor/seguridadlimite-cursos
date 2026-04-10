package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisosTrabajoAlturasPorFechaUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsultarPermisosTrabajoAlturasPorFechaService implements ConsultarPermisosTrabajoAlturasPorFechaUseCase {
    
    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;
    
    @Override
    @Transactional(readOnly = true)
    public List<PermisoTrabajoAlturas> ejecutar(String fecha) {
        List<PermisoTrabajoAlturas> permisos =
                permisoTrabajoAlturasPort.findPermisosVigentesEnFechaWithPermisoFechas(fecha);
        for (PermisoTrabajoAlturas p : permisos) {
            if (p.getPermisoFechas() != null) {
                Hibernate.initialize(p.getPermisoFechas());
            }
            if (p.getTiposTrabajo() != null) {
                Hibernate.initialize(p.getTiposTrabajo());
            }
            if (p.getPermisoDetalleChequeos() != null) {
                Hibernate.initialize(p.getPermisoDetalleChequeos());
            }
            if (p.getPermisoDetalleActividades() != null) {
                Hibernate.initialize(p.getPermisoDetalleActividades());
            }
        }
        return permisos;
    }
} 