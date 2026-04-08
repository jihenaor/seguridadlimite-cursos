package com.seguridadlimite.models.aprendiz.application;

import com.seguridadlimite.models.aprendiz.application.mapper.AprendizMapper;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizDTO;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarAprendizInscritosPorIdPermisoTrabajoService {
    private final ConsultarPermisoTrabajoAlturasUseCase consultarPermisoTrabajoAlturasUseCase;

    private final IAprendizDao dao;

    private final AprendizMapper mapper;

    public List<AprendizDTO> findByidpermisotrabajo(Integer idpermiso) {
        PermisoTrabajoAlturas permisoTrabajoAlturas = consultarPermisoTrabajoAlturasUseCase.consultar(idpermiso);

        List<Aprendiz> aprendices = dao.findAprendicesFechaUltimaAsistenciaIdNivel(
                permisoTrabajoAlturas.getValidodesde(),
                permisoTrabajoAlturas.getValidohasta(),
                permisoTrabajoAlturas.getIdNivel()
        );

        return aprendices.stream()
                .map(mapper::toDTO)
                .toList();
    }
}