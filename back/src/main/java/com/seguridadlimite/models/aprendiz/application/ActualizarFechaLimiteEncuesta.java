package com.seguridadlimite.models.aprendiz.application;

import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Service
@AllArgsConstructor
public class ActualizarFechaLimiteEncuesta {

    private final IAprendizDao dao;

    @Transactional
    public void update(LocalDate fecha) throws BusinessException {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);

        int cont = dao.updateFechaLimiteEncuesta(fecha, calendar.getTime());

        if (cont == 0) {
            throw new BusinessException("No se actualizó la fecha de evaluacion de NINGÚN aprendiz");
        }
    }
}
