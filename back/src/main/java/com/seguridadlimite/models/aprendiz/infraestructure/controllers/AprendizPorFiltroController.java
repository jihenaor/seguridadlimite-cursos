package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.findByFiltro.AprendizFindByFilter;
import com.seguridadlimite.models.aprendiz.domain.AprendizEvaluacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aprendiz/")
@RequiredArgsConstructor
public class AprendizPorFiltroController {

	private final AprendizFindByFilter service;

	@GetMapping("/{filtro}/filtro")
	@ResponseStatus(HttpStatus.OK)
	public List<AprendizEvaluacionDTO> getByNumerodocumento(@PathVariable String filtro) {

		return service.findByFiltro(filtro.trim());
	}
}
