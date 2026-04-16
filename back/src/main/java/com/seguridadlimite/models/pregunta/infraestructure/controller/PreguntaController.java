package com.seguridadlimite.models.pregunta.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.pregunta.application.PreguntaServiceImpl;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pregunta")
@RequiredArgsConstructor
public class PreguntaController extends Controller {

	private final PreguntaServiceImpl service;


	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Pregunta> getById(@PathVariable Long id) {
		Pregunta pregunta = service.findById(id);
		return new ResponseEntity<>(pregunta, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{idPregunta}/respuesta/{idRespuesta}")
	public ResponseEntity<Void> deleteRespuesta(
			@PathVariable Long idPregunta,
			@PathVariable Long idRespuesta) {
		try {
			service.deleteRespuesta(idPregunta, idRespuesta);
			return ResponseEntity.noContent().build();
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
