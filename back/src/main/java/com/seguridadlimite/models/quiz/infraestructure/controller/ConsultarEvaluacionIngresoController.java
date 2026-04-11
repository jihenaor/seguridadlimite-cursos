package com.seguridadlimite.models.quiz.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.pregunta.domain.TipoevaluacionEnum;
import com.seguridadlimite.models.quiz.application.ConsultarEvaluacionTeoricaService;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion")
@RequiredArgsConstructor
public class ConsultarEvaluacionIngresoController {

	private final ConsultarEvaluacionTeoricaService service;
	@GetMapping("/{idaprendiz}/ingreso")
	public List<Pregunta> consultarevaluacionconocimientostecnicos(
			@PathVariable Long idaprendiz) throws BusinessException {
		return service.findPreguntasAprendiz(idaprendiz,
				TipoevaluacionEnum.INGRESO.getEquivalente());
	}
}
