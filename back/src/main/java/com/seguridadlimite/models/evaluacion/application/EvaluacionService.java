package com.seguridadlimite.models.evaluacion.application;

import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.evaluacion.dominio.EvaluacionDTO;
import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.evaluacion.dominio.EvaluacionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class EvaluacionService {
    private final IEvaluacionDao evaluacionDao;
    private final EvaluacionMapper evaluacionMapper;

    public List<EvaluacionDTO> findByAprendiz(Long idaprendiz, String tipoevaluacion) {
        return evaluacionDao.findEvaluacionAprendiz(idaprendiz, tipoevaluacion).stream()
                .map(evaluacionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EvaluacionDTO> saveAll(List<Evaluacion> evaluaciones) {
        List<Evaluacion> savedEvaluaciones = new ArrayList<>();
        evaluacionDao.saveAll(evaluaciones).forEach(savedEvaluaciones::add);
        return savedEvaluaciones.stream()
                .map(evaluacionMapper::toDto)
                .collect(Collectors.toList());
    }
} 