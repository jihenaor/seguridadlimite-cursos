package com.seguridadlimite.models.parametros.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.ActualizarFechaLimiteEncuesta;
import com.seguridadlimite.models.parametros.application.UpdateEvaluationDate.UpdateEncuestaDate;
import com.seguridadlimite.models.parametros.dominio.Parametros;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parametros")
@RequiredArgsConstructor
public class UpdateEncuestaDateController extends Controller {

	private final UpdateEncuestaDate service;

	private final ActualizarFechaLimiteEncuesta actualizarFechaLimiteEncuesta;

	@PostMapping(path = "/updateEncuestaDate", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Parametros> updateEvaluationDate() throws BusinessException {

		Parametros parametros = service.update();

		// actualizarFechaLimiteEncuesta.update(parametros.getFechainscripcion());

		return ResponseEntity.ok(parametros);
	}
}
