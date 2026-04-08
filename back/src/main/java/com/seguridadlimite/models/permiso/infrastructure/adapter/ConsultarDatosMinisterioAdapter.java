package com.seguridadlimite.models.permiso.infrastructure.adapter;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infrastructure.repository.AprendizRepository;
import com.seguridadlimite.models.permiso.application.port.out.ConsultarDatosMinisterioPort;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.infrastructure.repository.PermisoTrabajoAlturasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultarDatosMinisterioAdapter implements ConsultarDatosMinisterioPort {

    private final PermisoTrabajoAlturasRepository permisoTrabajoAlturasRepository;
    private final AprendizRepository aprendizRepository;

    @Override
    public List<PermisoTrabajoAlturas> buscarPermisosPorIds(List<Integer> idPermisosTrabajos) {
        return permisoTrabajoAlturasRepository.findAllById(idPermisosTrabajos);
    }

    @Override
    public List<Aprendiz> buscarAprendicesPorIdPermiso(Integer idPermiso) {
        return aprendizRepository.findByIdPermisoWithTrabajador(idPermiso);
    }
}
