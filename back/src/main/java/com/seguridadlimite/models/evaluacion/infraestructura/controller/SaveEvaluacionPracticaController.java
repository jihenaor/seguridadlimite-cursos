package com.seguridadlimite.models.evaluacion.infraestructura.controller;

import com.seguridadlimite.models.evaluacion.application.SaveEvaluacionPracticaService;
import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion")
public class SaveEvaluacionPracticaController {

	@Autowired
	private SaveEvaluacionPracticaService service;

	@PostMapping(path = "/practica", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT) // No retorna un cuerpo en caso de éxito
	public void createEvaluacionPractica(@RequestBody List<Grupopregunta> evaluacions) {
		service.saveEvaluacionPractica(evaluacions);
	}
}
