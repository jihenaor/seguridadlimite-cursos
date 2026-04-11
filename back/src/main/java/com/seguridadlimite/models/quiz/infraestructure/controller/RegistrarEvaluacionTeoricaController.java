package com.seguridadlimite.models.quiz.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.pojo.RespuestaEvaluacion;
import com.seguridadlimite.models.evaluacion.application.RegistrarEvaluacionTeoricaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistrarEvaluacionTeoricaController {

	private final RegistrarEvaluacionTeoricaService service;

	@PostMapping(path = "registrarevaluacionteorica/{idaprendiz}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RespuestaEvaluacion> saveevaluacionpracticamovil(
			@PathVariable Long idaprendiz,
			@RequestBody List<Pregunta> preguntas) throws Exception {

		RespuestaEvaluacion respuestaEvaluacion = service.saveevaluacion(preguntas, idaprendiz);
		return ResponseEntity.ok(respuestaEvaluacion);

	}


}
