package com.seguridadlimite.models.aprendiz.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.parametros.application.UpdateEvaluationDate.FindParametrosById;
import com.seguridadlimite.models.parametros.dominio.Parametros;
import com.seguridadlimite.models.parametros.infraestructure.IParametrosDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ActualizarFechaLimiteEvaluacion {

    private final IAprendizDao dao;

    @Autowired
    IParametrosDao iParametrosDao;

    @Autowired
    FindParametrosById findParametrosById;

    @Transactional
    public void update(String fechaInscripcionOEvaluacion) throws BusinessException {
        Date date = iParametrosDao.obtenerFechaActual();

        Parametros parametros = findParametrosById.find();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.HOUR, parametros.getHorasevaluacion());

        int cont = dao.updateFechaLimiteEvaluacion(fechaInscripcionOEvaluacion,
                calendar.getTime());

        if (cont == 0) {
            throw new BusinessException("No se actualizó la fecha de evaluacion de ningún aprendiz para la fecha de inscripcion " + fechaInscripcionOEvaluacion);
        }
    }
}
