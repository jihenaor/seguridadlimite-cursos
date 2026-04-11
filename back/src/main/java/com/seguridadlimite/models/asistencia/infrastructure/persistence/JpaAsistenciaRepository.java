package com.seguridadlimite.models.asistencia.infrastructure.persistence;

import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.asistencia.domain.port.out.AsistenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaAsistenciaRepository implements AsistenciaRepository {

    private final IAsistenciaDao asistenciaDao;

    @Override
    public Asistencia findById(int id) {
        return asistenciaDao.findById((long) id).orElseThrow();
    }

    @Override
    public void updateFecha(Asistencia asistencia) {
        asistenciaDao.updateFecha(asistencia.getFecha().toString(), asistencia.getId());
    }

    @Override
    public int countByIdaprendizAndFechaIsNull(int idaprendiz) {
        return asistenciaDao.countByIdaprendizAndFechaIsNull(idaprendiz);
    }


} 