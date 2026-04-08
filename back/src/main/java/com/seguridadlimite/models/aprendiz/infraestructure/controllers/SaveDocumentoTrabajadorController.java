package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz.UpdateDocumentoTrabajadorService;
import com.seguridadlimite.models.entity.Trabajadordocumento;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@ResponseBody
public class SaveDocumentoTrabajadorController extends Controller {

	@Autowired
	private UpdateDocumentoTrabajadorService service;

	@PostMapping("/saveDocumentotrabajador")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Trabajadordocumento> saveDocumentotrabajador(@RequestBody Trabajadordocumento entity) throws IOException {

		service.saveDocumentotrabajador(entity);

		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
}
