package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisosTrabajoAlturasPorFechasUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultarPermisosTrabajoAlturasPorFechasService implements ConsultarPermisosTrabajoAlturasPorFechasUseCase {
    
    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;
    
    @Override
    @Transactional(readOnly = true)
    public List<PermisoTrabajoAlturas> ejecutar(String fechaInicio, String fechaFinal) {
        if (fechaInicio == null && fechaFinal == null) {
            return permisoTrabajoAlturasPort.findUltimoIngreso();
        }
        
        return permisoTrabajoAlturasPort.findByFechaInicioBetween(fechaInicio, fechaFinal);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PermisoTrabajoAlturas> ejecutarPorCodigoMinisterio(String codigoministerio) {

        Optional<PermisoTrabajoAlturas>  permisoTrabajoAlturas = permisoTrabajoAlturasPort.findByCodigoministerio(codigoministerio);

        if (permisoTrabajoAlturas.isEmpty()) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(Arrays.asList(permisoTrabajoAlturas.get()));
        }
    }
} 