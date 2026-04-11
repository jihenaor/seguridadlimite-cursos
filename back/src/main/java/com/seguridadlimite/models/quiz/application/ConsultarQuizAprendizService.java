package com.seguridadlimite.models.quiz.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultarQuizAprendizService {

	private final IEvaluacionDao dao;


	public List<Pregunta> findPreguntasAprendiz(
			int idaprendiz,
			String tipoEvaluacion,
			int numero) throws BusinessException {
		List<Evaluacion> evaluaciones = getEvaluaciones(idaprendiz, tipoEvaluacion, numero);

		return evaluaciones.stream()
				.map(ev -> {
					ev.getPregunta().setRespuestacorrecta(ev.getRespuestacorrecta());
					ev.getPregunta().setNumerorespuesta(ev.getNumerorespuesta());
					ev.getPregunta().setTextorespuesta(ev.getTextorespuesta());
					return ev.getPregunta();
				})
				.collect(Collectors.toList());
	}

	private List<Evaluacion> getEvaluaciones(int idaprendiz,
											 String tipoEvaluacion,
											 Integer numero) {
		if (numero == null) {
			return dao.findEvaluacionTipo(idaprendiz, tipoEvaluacion);
		} else {
			List<String> tipo = new ArrayList<>();

			tipo.add(tipoEvaluacion);
			if (tipoEvaluacion.equals("T")) {
				tipo.add("E");
			}

			return dao.findEvaluacionTipo(idaprendiz, tipo, numero);
		}
	}
}
