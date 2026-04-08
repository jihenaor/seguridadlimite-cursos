package com.seguridadlimite.springboot.backend.apirest.controllers;

import com.seguridadlimite.models.aprendiz.application.findTrabajadorInscripcion.FindTrabajadorInscripcionCu;
import com.seguridadlimite.models.entity.TrabajadorInscripcionPojo;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@ResponseBody
public class TrabajadorInscripcionController extends Controller {

	@Autowired
	private FindTrabajadorInscripcionCu service;

	@GetMapping(path = "/trabajadorinscripcion/{numerodocumento}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<TrabajadorInscripcionPojo> getTrabajadorinscripcion(
			@PathVariable String numerodocumento) throws BusinessException {
		TrabajadorInscripcionPojo trabajadorInscripcion = service.findTrabajadorInscripcion(numerodocumento);

		return ResponseEntity.ok(trabajadorInscripcion);
	}
}
