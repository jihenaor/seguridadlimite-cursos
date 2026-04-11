package com.seguridadlimite.models.nivel.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.nivel.application.findInscripcionesAbiertas.FindInscripcionesAbiertasCu;
import com.seguridadlimite.models.nivel.domain.Nivel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nivel")
@RequiredArgsConstructor
public class NivelFindActivoFechaRestController {

	private final FindInscripcionesAbiertasCu service;


	@GetMapping("/activosfecha")
	public List<Nivel> findactivos() {
		return service.validar();
	}

}
