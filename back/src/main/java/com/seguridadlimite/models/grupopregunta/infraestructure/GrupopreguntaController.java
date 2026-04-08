package com.seguridadlimite.models.grupopregunta.infraestructure;

import com.seguridadlimite.models.grupopregunta.application.listargrupopregunta.GrupoPreguntaService;
import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grupopregunta")
public class GrupopreguntaController {

	@Autowired
	private GrupoPreguntaService service;
	

	@GetMapping
	public ResponseEntity<List<Grupopregunta>> index() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
}
