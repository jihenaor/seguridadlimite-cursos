package com.seguridadlimite.models.aprendiz.application;

import com.seguridadlimite.models.aprendiz.application.mapper.AprendizMapper;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.domain.AprendizDTO;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.asistencia.domain.port.out.AsistenciaRepository;
import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarAprendizUltimaAsistenciaCoincidePorIdPermisoTrabajoService {
    private final IAprendizDao dao;
    private final ConsultarPermisoTrabajoAlturasUseCase consultarPermisoTrabajoAlturasUseCase;
    private final AsistenciaRepository asistenciaRepository;

    private final AprendizMapper mapper;

    public List<AprendizDTO> findByidpermisotrabajo(Integer idpermiso) {

        PermisoTrabajoAlturas permisoTrabajoAlturas = consultarPermisoTrabajoAlturasUseCase.consultar(idpermiso);


        List<Aprendiz> aprendices = dao.findAprendicesAsistieronRangoFechas(
                permisoTrabajoAlturas.getValidodesde(),
                permisoTrabajoAlturas.getValidohasta(),
                permisoTrabajoAlturas.getIdNivel()
        );


        for (Aprendiz aprendiz: aprendices) {
            if (aprendiz.getIdPermiso() == null) {
                Long asistenciasPendientes = asistenciaRepository.countByIdaprendizAndFechaIsNull(AprendizId.toLong(aprendiz.getId()));

                if (asistenciasPendientes == 0) {
                    aprendiz.setAsistenciaCompleta(true);
                }
            }
        }

        return aprendices.stream()
                .map(mapper::toDTO)
                .toList();
    }
}