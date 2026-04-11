package com.seguridadlimite.springboot.backend.apirest.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.entity.Empresa;
import com.seguridadlimite.springboot.backend.apirest.services.EmpresaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmpresaController {

	private final EmpresaServiceImpl service;

	@GetMapping("/empresas")
	public List<Empresa> index() {
		return service.findAll();
	}

	@GetMapping("/empresasseleccionadas")
	public List<Empresa> findSeleccionada() {
		return service.findSeleccionadas();
	}

	@GetMapping("/empresa/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Empresa getById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping("saveEmpresa")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa create(@RequestBody Empresa entity) {
		return service.save(entity);
	}

	@PutMapping("updateEmpresa/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa update(@RequestBody Empresa entity, @PathVariable Long id) {
		Empresa cursoActual = service.findById(id);

//		cursoActual.setDuraciontotal(entity.getDuraciontotal());
		return service.save(cursoActual);
	}

	@DeleteMapping("/deleteEmpresa/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
