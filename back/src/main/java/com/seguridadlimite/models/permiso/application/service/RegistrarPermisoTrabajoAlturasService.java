package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.models.permiso.application.port.in.RegistrarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoDetalleChequeo;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrarPermisoTrabajoAlturasService implements RegistrarPermisoTrabajoAlturasUseCase {
    
    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;

    @Override
    @Transactional
    public PermisoTrabajoAlturas ejecutar(PermisoTrabajoAlturas permisoTrabajoAlturas) {

        // Configurar la relación bidireccional para las fechas
        if (permisoTrabajoAlturas.getPermisoFechas() != null) {
            permisoTrabajoAlturas.getPermisoFechas().forEach(permisoFecha -> {
                permisoFecha.setPermisoTrabajoAlturas(permisoTrabajoAlturas);
            });
        }

        // JPA manejará automáticamente las fechas con cascade
        PermisoTrabajoAlturas permisoGuardado = permisoTrabajoAlturasPort.save(permisoTrabajoAlturas);

        // registrarTiposTrabajo(permisoTrabajoAlturas, permisoGuardado);
        // registrarGruposChequeo(permisoGuardado);
        // registrarActividades(permisoTrabajoAlturas, permisoGuardado);

        return permisoGuardado;
    }

    private void registrarTiposTrabajo(PermisoTrabajoAlturas permisoTrabajoAlturas, PermisoTrabajoAlturas permisoGuardado) {
        if (permisoTrabajoAlturas.getTiposTrabajo() != null) {
            permisoTrabajoAlturas.getTiposTrabajo().forEach(permisoTipoTrabajo -> {
                permisoTipoTrabajo.setPermisoTrabajoAlturas(permisoGuardado);
            });
        }
    }

    private void registrarGruposChequeo(PermisoTrabajoAlturas permisoGuardado) {
        if (permisoGuardado.getPermisoDetalleChequeos() != null) {
            for (PermisoDetalleChequeo permisoDetalleChequeo : permisoGuardado.getPermisoDetalleChequeos()) {
                // permisoDetalleChequeo.setPermisoTrabajoAlturas(permisoGuardado);
                permisoDetalleChequeo.setRespuesta(permisoDetalleChequeo.getRespuesta());
            }
        }
    }

    private void registrarActividades(
            PermisoTrabajoAlturas permisoTrabajoAlturas,
            PermisoTrabajoAlturas permisoGuardado) {
        if (permisoTrabajoAlturas.getPermisoDetalleActividades() != null) {
            permisoTrabajoAlturas.getPermisoDetalleActividades().forEach(actividad -> {
                actividad.setPermisoTrabajoAlturas(permisoGuardado);
            });
        }
    }
} 