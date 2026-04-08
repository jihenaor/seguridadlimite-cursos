package com.seguridadlimite.models.disenocurricular.infraestructure.controller;

import com.seguridadlimite.models.disenocurricular.application.FindDisenocurricularByIdnivelService;
import com.seguridadlimite.models.disenocurricular.domain.Disenocurricular;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disenocurricular")
public class DisenocurricularController extends Controller {

	@Autowired
	private FindDisenocurricularByIdnivelService service;

	@GetMapping("/{idnivel}")
	public List<Disenocurricular> get(@PathVariable Long idnivel) {
		return service.find(idnivel);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody Disenocurricular disenocurricular) {
		service.save(disenocurricular);
	}
}
