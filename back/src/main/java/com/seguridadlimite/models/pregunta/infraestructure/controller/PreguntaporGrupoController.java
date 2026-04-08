package com.seguridadlimite.models.pregunta.infraestructure.controller;

import com.seguridadlimite.models.pregunta.application.findByGrupo.BuscarPreguntasGrupoService;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pregunta")
@AllArgsConstructor
public class PreguntaporGrupoController extends Controller {

	private final BuscarPreguntasGrupoService service;

	@GetMapping("/{idgrupo}/grupo")
	public ResponseEntity<List<Pregunta>> index(@PathVariable Long idgrupo) {
		return new ResponseEntity<>(
				service.find(idgrupo),
				HttpStatus.OK);
	}


}
