package com.seguridadlimite.models.personal.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.personal.application.PersonalService;
import com.seguridadlimite.models.personal.dominio.Personal;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonalRestController {

	private final PersonalService service;

	@GetMapping("/personals")
	public List<Personal> index() {
		List<Personal> l = service.findAll();

		return service.findAll();
	}
	
	@GetMapping("/personals/{tipo}")
	public List<Personal> findbytipo(@PathVariable String tipo) {
		return service.findByTipo(tipo);
	}

	@GetMapping("/personal/{id}")
	public Personal getById(@PathVariable Long id) {
		return service.findById(id);
	}

	@GetMapping("/personalnumerodocumento/{numerodocumento}")
	@ResponseStatus(HttpStatus.OK)
	public Personal getByNumerodocumento(@PathVariable String numerodocumento) {
		return service.findByNumerodocumento(numerodocumento);
	}
	
	@PostMapping("savePersonal")
	@ResponseStatus(HttpStatus.CREATED)
	public Personal create(@RequestBody Personal entity) {
		try {
			entity.setId(null);
			return service.mergeAndSave(entity);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("updatePersonal/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Personal update(@RequestBody Personal entity, @PathVariable Long id) {
		if (entity.getId() != null && !entity.getId().equals(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del cuerpo no coincide con la ruta");
		}
		try {
			entity.setId(id);
			return service.mergeAndSave(entity);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/*
	@DeleteMapping("/deletePersonal{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	*/

	@GetMapping("/personalsreport")
	public String personalreport() {
		String r;
		
		try {
			r = service.exporterReport("pdf");
		} catch (FileNotFoundException | JRException e) {
			// TODO Auto-generated catch block
			r = e.getMessage();
		}
		return r;
	}
}
