package com.seguridadlimite.models.parametros.infraestructure.controller;

import com.seguridadlimite.models.parametros.application.UpdateEvaluationDate.FindParametrosById;
import com.seguridadlimite.models.parametros.dominio.Parametros;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parametros")
public class FindParametrosController extends Controller {

	@Autowired
	private FindParametrosById service;


	@GetMapping()
	public ResponseEntity<Parametros> get() {
		return new ResponseEntity<>(service.find(), HttpStatus.OK) ;
	}
}
