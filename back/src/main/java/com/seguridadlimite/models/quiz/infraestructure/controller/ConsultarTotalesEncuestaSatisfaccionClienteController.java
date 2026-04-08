package com.seguridadlimite.models.quiz.infraestructure.controller;

import com.seguridadlimite.models.quiz.application.ConsultarTotalesEncuestaSatisfaccionService;
import com.seguridadlimite.models.quiz.infraestructure.projection.TotalesEncuestaSatisfaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/totalesencuesta")
public class ConsultarTotalesEncuestaSatisfaccionClienteController {

	@Autowired
	private ConsultarTotalesEncuestaSatisfaccionService service;

	@GetMapping("")
	public ResponseEntity<List<TotalesEncuestaSatisfaccion>> consultarevaluacionteorica(
			) {

		return ResponseEntity.ok(service.find());
	}
}
