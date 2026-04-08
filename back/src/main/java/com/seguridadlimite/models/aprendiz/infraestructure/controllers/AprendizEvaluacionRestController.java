package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.FindByNumerodocumentoEvaluacion;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.springboot.backend.apirest.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprendiz")
@ResponseBody
public class AprendizEvaluacionRestController extends Controller {

	@Autowired
	private FindByNumerodocumentoEvaluacion service;

	@Autowired
	MailServiceImpl mailService;

	@GetMapping(path = "/{numerodocumento}/evaluacionteorico", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Aprendiz> getEvaluacionTeorico(@PathVariable String numerodocumento) throws BusinessException {
		return ResponseEntity.ok(service.findByNumerodocumentoEvaluacionTeorica(numerodocumento));
	}

	@GetMapping(path = "/{numerodocumento}/evaluacionconocimientosprevios", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Aprendiz> getEvaluacion(@PathVariable String numerodocumento) throws BusinessException {
		return ResponseEntity.ok(service.findByNumerodocumentoEvaluacionConocimientosPrevios(numerodocumento));
	}

	@GetMapping(path = "/{numerodocumento}/evaluacionsatisfaccioncliente", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Aprendiz> getEvaluacionSatisfaccionCliente(@PathVariable String numerodocumento) throws BusinessException {
		return ResponseEntity.ok(service.findByNumerodocumentoEvaluacionEncuestaSatisfaccion(numerodocumento));
	}
}
