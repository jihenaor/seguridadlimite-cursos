package com.seguridadlimite.models.bloque.infraestructure;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.bloque.application.listarbloques.ListarBloqueService;
import com.seguridadlimite.models.bloque.model.Bloque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bloque")
@RequiredArgsConstructor
public class BloqueController {

	private final ListarBloqueService service;
	

	@GetMapping
	public ResponseEntity<List<Bloque>> index() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
}
