package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.updateAprendiz.UpdateAprendizService;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.entity.RespuestaWs;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprendiz")
@RequiredArgsConstructor
public class AprendizUpdatePerfilController {

	private final UpdateAprendizService service;

	@PostMapping("/updatePerfil")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RespuestaWs> updatePerfil(@RequestBody Aprendiz entity) throws BusinessException {
		service.update(entity);
		return new ResponseEntity<RespuestaWs>(new RespuestaWs(), HttpStatus.OK);
	}
}
