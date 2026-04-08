package com.seguridadlimite.springboot.backend.apirest.controllers;

import com.seguridadlimite.iservices.IProgramaService;
import com.seguridadlimite.models.programa.model.Programa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramaRestController {

	@Autowired
	private IProgramaService service;
	


	@GetMapping("/programas")
	public List<Programa> index() {
		return service.findAll();
	}

	@GetMapping("/programa/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Programa getById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping("savePrograma")
	@ResponseStatus(HttpStatus.CREATED)
	public Programa create(@RequestBody Programa entity) {
		return service.save(entity);
	}

	@PutMapping("updatePrograma/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Programa update(@RequestBody Programa entity, @PathVariable Long id) {
		Programa programaActual = service.findById(id);

		programaActual.setNombre(entity.getNombre());
		return service.save(programaActual);
	}

	@DeleteMapping("/deletePrograma{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
