package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.buscarporidgrupo.BuscarAprendizInscripcion;
import com.seguridadlimite.models.aprendiz.domain.AprendizEvaluacionDTO;
import com.seguridadlimite.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/aprendiz")
public class AprendizPorFechaInscripcionController {

	@Autowired
	private BuscarAprendizInscripcion service;

	@GetMapping("/inscripcion")
	@ResponseStatus(HttpStatus.OK)
	public List<AprendizEvaluacionDTO> findAprendicesInscripcion() {
		return service.buscar();
	}

	@GetMapping("/inscritoshoy")
	@ResponseStatus(HttpStatus.OK)
	public List<AprendizEvaluacionDTO> findAprendicesInscritos() {
		return service.buscarInscritosPorFecha(DateUtil.getCurrentDate());
	}
}
