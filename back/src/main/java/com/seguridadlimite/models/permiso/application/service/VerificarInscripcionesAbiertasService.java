package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificarInscripcionesAbiertasService {

    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;

    public boolean execute(String fecha) {
        return permisoTrabajoAlturasPort.existenInscripcionesAbiertas(fecha);
    }
}