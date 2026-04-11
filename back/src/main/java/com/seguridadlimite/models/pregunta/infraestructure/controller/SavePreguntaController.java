package com.seguridadlimite.models.pregunta.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.pregunta.application.SavePreguntaService;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pregunta")
@RequiredArgsConstructor
public class SavePreguntaController extends Controller {

	private final SavePreguntaService savePreguntaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pregunta> create(@RequestBody Pregunta entity) {
		Pregunta pregunta = savePreguntaService.save(entity);
		return new ResponseEntity<>(pregunta, HttpStatus.OK);
	}
}
