package com.seguridadlimite.models.aprendiz.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.parametros.application.UpdateEvaluationDate.FindParametrosById;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FindByNumerodocumentoEvaluacion {
    private IAprendizDao aprendizDao;

    private FindParametrosById findParametrosById;

    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;

    @Transactional(readOnly = true)
    public Aprendiz findByNumerodocumentoEvaluacionConocimientosPrevios(String numerodocumento) throws BusinessException {
        Aprendiz a = aprendizDao.findApredizDocumentoFechaLimiteInscripcion(numerodocumento)
                .stream()
                .findFirst()
                .orElse(null);;

        if (a == null) {
            throw new BusinessException("No existe evaluacion de ingreso activa o No existe un aprendiz con este documento.");
        }

        return a;
    }
    @Transactional(readOnly = true)
    public Aprendiz findByNumerodocumentoEvaluacionTeorica(String numerodocumento)  {

        List<Aprendiz> aprendiz = aprendizDao.findFirstEvaluacionActiva(numerodocumento, DateUtil.getCurrentDate());
        
        
        if (aprendiz.isEmpty()) {
            throw new BusinessException("No existe un aprendiz con este documento o No existe permiso de trabajo de alturas.");
        }

        return aprendiz.get(0);
    }

    @Transactional(readOnly = true)
    public Aprendiz findByNumerodocumentoEvaluacionEncuestaSatisfaccion(String numerodocumento) throws BusinessException {

        Aprendiz a = aprendizDao.findByIdtrabajadorEncuestaActiva(calcularFechaInicio(), numerodocumento);

        if (a == null) {
            throw new BusinessException("No existen encuestas activas o no existe un aprendiz con este documento.");
        }

        return a;
    }

    private int calcularTiempo(Date fechaEvaluacion) {
        Date fechaActual = new Date();

        // Restar la fecha anterior a la fecha actual
        long diferenciaMilisegundos = fechaEvaluacion.getTime() - fechaActual.getTime() ;
        return (int) (diferenciaMilisegundos / (60 * 1000));
    }

    public Date calcularFechaInicio() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        return cal.getTime();
    }
}
