package com.seguridadlimite.models.pregunta.infraestructure.controller;

import com.seguridadlimite.models.pregunta.application.PreguntaServiceImpl;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pregunta")
public class PreguntaController extends Controller {

	@Autowired
	private PreguntaServiceImpl service;


	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Pregunta> getById(@PathVariable Long id) {
		Pregunta pregunta = service.findById(id);
		return new ResponseEntity<>(pregunta, HttpStatus.OK);
	}
}
