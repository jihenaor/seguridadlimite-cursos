package com.seguridadlimite.models.quiz.infraestructure.controller;

import com.seguridadlimite.models.pregunta.domain.TipoevaluacionEnum;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.models.quiz.application.ConsultarEvaluacionTeoricaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion")
public class ConsultarEvaluacionTeoricaController {

	@Autowired
	private ConsultarEvaluacionTeoricaService service;

	@GetMapping("/{idaprendiz}/teorica")
	public ResponseEntity<List<Pregunta>> consultarevaluacionteorica(
			@PathVariable Long idaprendiz) throws BusinessException {
		List<Pregunta> preguntas = service.findPreguntasAprendiz(
					idaprendiz,
					TipoevaluacionEnum.TEORICO.getEquivalente());

		return new ResponseEntity<>(preguntas, HttpStatus.OK);
	}
}
