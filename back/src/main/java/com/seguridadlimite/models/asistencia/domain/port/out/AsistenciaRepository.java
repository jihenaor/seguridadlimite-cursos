package com.seguridadlimite.models.asistencia.domain.port.out;

import com.seguridadlimite.models.asistencia.domain.Asistencia;

public interface AsistenciaRepository {
    Asistencia findById(long id);
    void updateFecha(Asistencia asistencia);
    Long countByIdaprendizAndFechaIsNull(Long idaprendiz);
} 