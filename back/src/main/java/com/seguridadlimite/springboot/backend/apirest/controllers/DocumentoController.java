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

import com.seguridadlimite.models.documentos.domain.Documento;
import com.seguridadlimite.models.documentos.application.DocumentoServiceImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class DocumentoController {

	@Autowired
	private DocumentoServiceImpl service;

	@GetMapping("/documentos")
	public List<Documento> index() {
		return service.findAll();
	}
	
	@GetMapping("/documento/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Documento getById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping("saveDocumento")
	@ResponseStatus(HttpStatus.CREATED)
	public Documento create(@RequestBody Documento entity) {
		return service.save(entity);
	}

	@PutMapping("updateDocumento/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Documento update(@RequestBody Documento entity, @PathVariable Long id) {
		Documento cursoActual = service.findById(id);

		//cursoActual.setDuraciontotal(entity.getDuraciontotal());
		return service.save(cursoActual);
	}

	@DeleteMapping("/deleteDocumento/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
