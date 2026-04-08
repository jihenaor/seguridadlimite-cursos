package com.seguridadlimite.models.nivel.infraestructure.controller;

import com.seguridadlimite.iservices.INivelInscripcionService;
import com.seguridadlimite.iservices.INivelService;
import com.seguridadlimite.models.nivel.domain.Nivel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nivel")
@AllArgsConstructor
public class NivelRestController {

	private final INivelService service;

	private final INivelInscripcionService serviceInscripcion;

	@GetMapping()
	public List<Nivel> index() {
		return service.findAll();
	}

	@GetMapping("/activos")
	public List<Nivel> findactivos() {
		return service.findActivos();
	}

	@GetMapping("/activosinscripcion")
	public List<Nivel> findactivosinscripcion() {
		return serviceInscripcion.findActivos();
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Nivel create(@RequestBody Nivel entity) {
		return service.save(entity);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Nivel update(@RequestBody Nivel entity, @PathVariable Long id) {
//		Nivel programaActual = service.findById(id);

//		programaActual.setNombre(entity.getNombre());
//s		return service.save(programaActual);
		return null;
	}

}
