package com.seguridadlimite.models.aprendiz.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FindByNumerodocumentoEvaluacion {
    private IAprendizDao aprendizDao;

    @Transactional(readOnly = true)
    public Aprendiz findByNumerodocumentoEvaluacionConocimientosPrevios(String numerodocumento) throws BusinessException {
        Aprendiz a = aprendizDao.findApredizDocumentoFechaLimiteInscripcion(numerodocumento)
                .stream()
                .findFirst()
                .orElse(null);

        if (a == null) {
            List<Aprendiz> porDocumento = aprendizDao.findBynumerodocumento(numerodocumento);
            if (porDocumento.isEmpty()) {
                throw new BusinessException(
                        "No hay ningún aprendiz inscrito con el número de documento indicado. "
                                + "Revise que lo haya digitado bien (sin puntos ni espacios de más) y que corresponda al documento con el que se inscribió.");
            }
            Date limite = porDocumento.stream()
                    .map(Aprendiz::getFechalimiteinscripcion)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder())
                    .orElse(null);
            if (limite != null) {
                String limiteTexto = DateUtil.dateToString(limite, "dd/MM/yyyy HH:mm");
                throw new BusinessException(
                        "Existe registro de aprendiz con este documento, pero ya no está dentro del plazo para la evaluación de ingreso. "
                                + "La fecha límite de inscripción registrada en el sistema es " + limiteTexto
                                + " (hora Colombia). Si cree que es un error, comuníquese con la entidad de formación.");
            }
            throw new BusinessException(
                    "Existe registro de aprendiz con este documento, pero no está habilitado para la evaluación de ingreso: "
                            + "no consta una fecha límite de inscripción en el registro o no cumple las condiciones de vigencia. "
                            + "Comuníquese con la entidad de formación.");        }

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

    public Date calcularFechaInicio() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        return cal.getTime();
    }
}
