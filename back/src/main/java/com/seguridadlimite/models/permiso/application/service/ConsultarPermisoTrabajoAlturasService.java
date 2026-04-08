package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultarPermisoTrabajoAlturasService implements ConsultarPermisoTrabajoAlturasUseCase {
    
    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;

    @Override
    @Transactional(readOnly = true)
    public PermisoTrabajoAlturas consultar(Integer idPermiso) {
        // 1. Obtener el permiso de trabajo en alturas (incluye los detalles de chequeo por EAGER)
        return permisoTrabajoAlturasPort.findById(idPermiso)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PermisoTrabajoAlturas> consultarCodigoministerio(String codigoministerio) {
        return permisoTrabajoAlturasPort.findByCodigoministerio(codigoministerio);
    }

    @Override
    public List<PermisoTrabajoAlturas> findAll() {
        return permisoTrabajoAlturasPort.findAll();
    }
} 