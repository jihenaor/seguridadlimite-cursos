package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz.UpdateDocumentoaprendizServiceImpl;
import com.seguridadlimite.models.documentos.domain.Documento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DocumentoaprendizController {

	@Autowired
	private UpdateDocumentoaprendizServiceImpl service;

	@PostMapping(path = "/saveDocumentoaprendiz", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<List<Documento>> create(@RequestBody List<Documento> documentos) {
			service.save(documentos);
			
			return ResponseEntity.ok(documentos);
	}
}
