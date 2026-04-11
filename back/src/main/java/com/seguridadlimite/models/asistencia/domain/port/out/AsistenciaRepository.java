package com.seguridadlimite.models.asistencia.domain.port.out;

import com.seguridadlimite.models.asistencia.domain.Asistencia;

public interface AsistenciaRepository {
    Asistencia findById(int id);
    void updateFecha(Asistencia asistencia);
    int countByIdaprendizAndFechaIsNull(int idaprendiz);
} 