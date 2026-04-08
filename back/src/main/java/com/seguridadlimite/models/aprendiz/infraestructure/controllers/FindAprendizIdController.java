package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.AprendizServicerImpl;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aprendiz")
public class FindAprendizIdController {

	@Autowired
	private AprendizServicerImpl service;

	@GetMapping("/{id}")
	public Aprendiz getById(@PathVariable Long id) {
		return service.findById(id);
	}

}
