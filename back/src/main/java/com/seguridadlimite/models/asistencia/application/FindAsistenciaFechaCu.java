package com.seguridadlimite.models.asistencia.application;

import com.seguridadlimite.models.asistencia.infrastructure.persistence.IAsistenciaDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAsistenciaFechaCu {
    private final IAsistenciaDao dao;

    public List<Long> find(String fechadesde, String fechahasta) {
        return dao.findIdAprendizByFecha(fechadesde, fechahasta);
    }
}
