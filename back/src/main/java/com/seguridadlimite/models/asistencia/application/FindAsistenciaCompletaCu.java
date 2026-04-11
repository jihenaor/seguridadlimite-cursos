package com.seguridadlimite.models.asistencia.application;

import com.seguridadlimite.models.asistencia.infrastructure.persistence.IAsistenciaDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindAsistenciaCompletaCu {

    private final IAsistenciaDao dao;

    public boolean find(int idaprendiz) {
        return dao.countByIdaprendiz(idaprendiz) > 0 &&
                dao.countByIdaprendizAndFechaIsNull(idaprendiz) == 0;
    }
}
