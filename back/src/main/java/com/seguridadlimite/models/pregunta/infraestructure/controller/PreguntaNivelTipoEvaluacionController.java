package com.seguridadlimite.models.pregunta.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.pregunta.application.findByNiveltipoevaluacion.BuscarPreguntasNivelTipoEvaluacion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PreguntaNivelTipoEvaluacionController extends Controller {

	private final BuscarPreguntasNivelTipoEvaluacion service;

	@GetMapping("/preguntarniveltipoevaluacion/{idnivel}/{tipoevaluacion}")
	public ResponseEntity<List<Pregunta>> index(@PathVariable Long idnivel, @PathVariable String tipoevaluacion) {
		return new ResponseEntity<>(
				service.findByNiveltipoevaluacion(idnivel, tipoevaluacion),
				HttpStatus.OK);
	}


}
