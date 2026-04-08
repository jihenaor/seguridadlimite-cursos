package com.seguridadlimite.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seguridadlimite.iservices.IGrupopreguntaService;
import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class GrupopreguntaRestController {

	@Autowired
	private IGrupopreguntaService service;

	@GetMapping("/grupopreguntas")
	public List<Grupopregunta> index() {
		return service.findAll();
	}

	@GetMapping("/grupopregunta/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Grupopregunta getById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping("saveGrupopregunta")
	@ResponseStatus(HttpStatus.CREATED)
	public Grupopregunta create(@RequestBody Grupopregunta entity) {
		return service.save(entity);
	}

	@PutMapping("updateGrupopregunta/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Grupopregunta update(@RequestBody Grupopregunta entity, @PathVariable Long id) {
		Grupopregunta grupopreguntaActual = service.findById(id);

		//cursoActual.setDuraciontotal(entity.getDuraciontotal());
		return service.save(grupopreguntaActual);
	}

	@DeleteMapping("/deleteGrupopregunta/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
