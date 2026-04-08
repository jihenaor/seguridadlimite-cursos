package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.buscarpordocumentoparainscripcion.FindByNumerodocumentoParaInscripcion;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprendiz")
@ResponseBody
@AllArgsConstructor
public class AprendizdocumentoController extends Controller {

	private final FindByNumerodocumentoParaInscripcion service;

	@GetMapping(path = "/{numerodocumento}/inscripcion", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Aprendiz> findA(@PathVariable String numerodocumento) throws BusinessException {
		return ResponseEntity.ok(service.find(numerodocumento));
	}
}
