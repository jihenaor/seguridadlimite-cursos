package com.seguridadlimite.models.quiz.infraestructure.controller;

import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.quiz.application.ConsultarQuizAprendizService;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion")
@AllArgsConstructor
public class ConsultarQuizAprendizController {

	private final ConsultarQuizAprendizService service;

	@GetMapping("/{idaprendiz}/{tipoevaluacion}/{numero}/tipoevaluacion")
	public ResponseEntity<List<Pregunta>> consultarevaluacion(
			@PathVariable int idaprendiz,
			@PathVariable String tipoevaluacion,
			@PathVariable int numero) throws BusinessException {
		List<Pregunta> preguntas = service.findPreguntasAprendiz(
					idaprendiz,
					tipoevaluacion,
					numero);

		return new ResponseEntity<>(preguntas, HttpStatus.OK);
	}
}
