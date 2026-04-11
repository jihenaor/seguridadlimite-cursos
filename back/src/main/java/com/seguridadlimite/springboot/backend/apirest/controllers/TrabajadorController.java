package com.seguridadlimite.springboot.backend.apirest.controllers;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.AprendizServicerImpl;
import com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz.DocumentoaprendizServiceImpl;
import com.seguridadlimite.models.documentos.application.DocumentoServiceImpl;
import com.seguridadlimite.models.trabajador.application.TrabajadorFindByDocumentoCu;
import com.seguridadlimite.models.trabajador.application.TrabajadorSaveCu;
import com.seguridadlimite.models.trabajador.application.TrabajadorfindByNumerodocumentoIdGrupoCu;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.services.AprendizServiceImpl;
import com.seguridadlimite.springboot.backend.apirest.services.MailServiceImpl;
import com.seguridadlimite.models.trabajador.application.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class TrabajadorController extends Controller {

	private final TrabajadorService service;

	@Autowired
	TrabajadorFindByDocumentoCu trabajadorFindByDocumentoCu;

	@Autowired
	TrabajadorfindByNumerodocumentoIdGrupoCu trabajadorfindByNumerodocumentoIdGrupoCu;


	private final TrabajadorSaveCu trabajadorSaveCu;
	
	private final AprendizServicerImpl aprendizService;
	
	private final AprendizServiceImpl aprendiService;
	
	private final DocumentoaprendizServiceImpl documentoAprendizService;
	
	private final DocumentoServiceImpl documentoService;
	
	@Autowired
	MailServiceImpl mailService;

	@GetMapping("/trabajadors")
	public List<Trabajador> index() {
		
		List<Trabajador> l = service.findAll();

		return l;
	}
	
	@GetMapping("/trabajadorsgruposactivos")
	public List<Trabajador> trabajadoresgruposactivos() {
		
		return service.findTrabajadoresGruposActivos();
	}

	@GetMapping(path = "/trabajadornumerodocumento/{numerodocumento}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Trabajador> getByNumerodocumento(@PathVariable String numerodocumento) {
		Trabajador t;
		
		t = trabajadorFindByDocumentoCu.findByNumerodocumento(numerodocumento);
		if (t == null) {
			t = new Trabajador();
		}
		return ResponseEntity.ok(t);
	}
	
	@GetMapping(path = "/trabajadornumerodocumento/{numerodocumento}/{idgrupo}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Trabajador> getByNumerodocumento(
			@PathVariable String numerodocumento,
			@PathVariable Long idgrupo) {
		Trabajador t;
		
		t = trabajadorfindByNumerodocumentoIdGrupoCu.findByNumerodocumento(numerodocumento, idgrupo);
		if (t == null) {
			t = new Trabajador();
		}
		return new ResponseEntity<Trabajador>(t, HttpStatus.OK);
	}

	@PostMapping("saveTrabajador")
	@ResponseStatus(HttpStatus.CREATED)
	public Trabajador create(@RequestBody Trabajador entity) {
		entity.setCreateAt(new Date());
		entity.setUpdateAt(new Date());
		return trabajadorSaveCu.save(entity);
	}

	@PostMapping(path = "updateTrabajador/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Trabajador> update(@RequestBody Trabajador entity, @PathVariable Long id) {
		entity.setUpdateAt(new Date());

		Trabajador t = service.findById(id);

		try {
			copiarEntidad(entity, t, false);
		} catch (Exception e1) {
			log.error("Se capturó una excepción: ", e1);
		}

		try {
			t = trabajadorSaveCu.save(t);
		} catch (Exception e) {
			log.error("Se capturó una excepción: ", e);
		}

		return new ResponseEntity<>(t, HttpStatus.OK);
	}

	@DeleteMapping("/deleteTrabajador{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		// service.delete(id);
	}
	

}
