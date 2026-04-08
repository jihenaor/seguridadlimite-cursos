package com.seguridadlimite.models.asistencia.application.service;

import com.seguridadlimite.models.aprendiz.application.findById.FindAprendizByIdService;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.asistencia.domain.port.in.UpdateAsistenciaUseCase;
import com.seguridadlimite.models.asistencia.domain.port.out.AsistenciaRepository;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateAsistenciaService implements UpdateAsistenciaUseCase {

    private final AsistenciaRepository asistenciaRepository;
    private final FindAprendizByIdService findAprendizByIdService;
    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;
    private final IAprendizDao aprendizDao;

    @Override
    @Transactional
    public void update(Asistencia asistencia) {
        // 1. Actualizar la fecha de asistencia

        if (asistencia.getSelected()) {
            Long asistenciasPendientes = asistenciaRepository.countByIdaprendizAndFechaIsNull(asistencia.getIdaprendiz());

            if (asistenciasPendientes == 0) {
                Aprendiz aprendiz = findAprendizByIdService.find(asistencia.getIdaprendiz());

                List<PermisoTrabajoAlturas> permisoTrabajoAlturas = permisoTrabajoAlturasPort.findPermisosVigentesEnFecha(asistencia.getFecha(), aprendiz.getIdnivel());

                if (permisoTrabajoAlturas.isEmpty()) {
                    throw new BusinessException("No existe un permiso de trabajo Valido para la fecha " + asistencia.getFecha() + ". Valide el cupo");
                } else {
                    aprendiz.setIdPermiso(permisoTrabajoAlturas.get(0).getIdPermiso());

                    aprendizDao.save(aprendiz);
                }
            }
        }

        asistenciaRepository.updateFecha(asistencia);
    }
} 