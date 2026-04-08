package com.seguridadlimite.models.asistencia.application;

import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.asistencia.infrastructure.persistence.IAsistenciaDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAsistenciaAprendizCu {
    private final IAsistenciaDao dao;

    public List<Asistencia> find(Long idaprendiz) {
        return dao.findByIdaprendiz(idaprendiz);
    }
}
