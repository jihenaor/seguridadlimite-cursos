package com.seguridadlimite.springboot.backend.apirest.controllers;

import com.seguridadlimite.models.enfasis.domain.Enfasis;
import com.seguridadlimite.springboot.backend.apirest.services.EnfasisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EnfasisRestController {

	@Autowired
	private EnfasisServiceImpl service;

	@GetMapping("/enfasis")
	public List<Enfasis> index() {
		return service.findAll();
	}

	@GetMapping("/enfasis-inscripcion")
	public List<Enfasis> indexActivo() {
		return service.findAll().stream().filter(enfasis -> "A".equals(enfasis.getEstado()) ).toList();
	}

	@GetMapping("/enfasis/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Enfasis getById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping("saveEnfasis")
	@ResponseStatus(HttpStatus.CREATED)
	public Enfasis create(@RequestBody Enfasis entity) {
		return service.save(entity);
	}

	@PutMapping("updateEnfasis/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Enfasis update(@RequestBody Enfasis entity, @PathVariable Long id) {
		Enfasis programaActual = service.findById(id);

		programaActual.setNombre(entity.getNombre());
		return service.save(programaActual);
	}

	@DeleteMapping("/deleteEnfasis/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
