package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisosTrabajoAlturasPorFechaUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import lombok.AllArgsConstructor;
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
        return permisoTrabajoAlturasPort.findPermisosVigentesEnFechaWithPermisoFechas(fecha);
    }
} 