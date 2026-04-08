package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.findAprendizCertificate.findByFiltro.FindAprendizCertificate;
import com.seguridadlimite.models.aprendiz.domain.AprendizEvaluacionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AprendizCertificadoController {

	private FindAprendizCertificate service;

	@GetMapping("/aprendizcertificate/{filtro}")
	@ResponseStatus(HttpStatus.OK)
	public List<AprendizEvaluacionDTO> getByNumerodocumento(@PathVariable String filtro) {
		return service.findByFiltro(filtro);
	}

	@GetMapping("/empresafindcertificate/{filtro}/{fechaInicial}/{fechaFinal}")
	public ResponseEntity<List<AprendizEvaluacionDTO>> buscarPorFiltro(
			@PathVariable String filtro,
			@PathVariable String fechaInicial,
			@PathVariable String fechaFinal) {
		return ResponseEntity.ok(service.findByNit(
				filtro, fechaInicial, fechaFinal));
	}
}
