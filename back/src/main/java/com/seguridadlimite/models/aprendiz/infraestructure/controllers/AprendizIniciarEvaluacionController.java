package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.inicializarEvaluacionAprendiz.InicializarEvaluacionAprendiz;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprendiz")
@ResponseBody
@RequiredArgsConstructor
public class AprendizIniciarEvaluacionController extends Controller {

	private final InicializarEvaluacionAprendiz service;

	@PutMapping(path = "/{numerodocumento}/{tipoevaluacion}/inicializar", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> updateEvaluacion(
			@PathVariable String numerodocumento,
			@PathVariable String tipoevaluacion) throws Exception {
		service.update(numerodocumento, tipoevaluacion, null);
		return ResponseEntity.ok("");
	}
}
