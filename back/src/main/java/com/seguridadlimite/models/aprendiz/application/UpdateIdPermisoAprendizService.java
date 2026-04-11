package com.seguridadlimite.models.aprendiz.application;

import com.seguridadlimite.models.aprendiz.application.findById.FindAprendizByIdService;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.asistencia.infrastructure.persistence.IAsistenciaDao;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateIdPermisoAprendizService {
    private final IAsistenciaDao iAsistenciaDao;
    private final FindAprendizByIdService findAprendizByIdService;
    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;
    private final IAprendizDao aprendizDao;

    @Transactional
    public void update(int idAprendiz, int idPermiso) {
        List<Asistencia> asistencias = iAsistenciaDao.findByIdaprendiz(idAprendiz);

        if (asistencias.isEmpty()) {
            throw new BusinessException("No existen asistencias para el aprendiz");
        }

        Long asistenciasPendientes = asistencias.stream()
                .filter(a -> a.getFecha() == null)
                .count();


        if (asistenciasPendientes == 0) {
            Aprendiz aprendiz = findAprendizByIdService.find(idAprendiz);

            PermisoTrabajoAlturas permisoTrabajoAlturas = permisoTrabajoAlturasPort.findById(idPermiso).orElseThrow();
            aprendiz.setIdPermiso(permisoTrabajoAlturas.getIdPermiso());

            aprendizDao.save(aprendiz);
        } else {
            throw new BusinessException("La asistencia no esta completa");
        }
    }
} 