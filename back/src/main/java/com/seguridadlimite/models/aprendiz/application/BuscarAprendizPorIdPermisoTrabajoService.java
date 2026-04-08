package com.seguridadlimite.models.aprendiz.application;

import com.seguridadlimite.models.aprendiz.application.mapper.AprendizMapper;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizDTO;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarAprendizPorIdPermisoTrabajoService {
    private final IAprendizDao dao;

    private final AprendizMapper mapper;

    public List<AprendizDTO> findByidpermisotrabajo(Integer idpermiso) {
        List<Aprendiz> aprendices = dao.findByIdPermiso(idpermiso);

        return aprendices.stream()
                .map(mapper::toDTO)
                .toList();
    }
}