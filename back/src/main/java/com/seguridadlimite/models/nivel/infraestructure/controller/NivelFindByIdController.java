package com.seguridadlimite.models.nivel.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.nivel.domain.Nivel;
import com.seguridadlimite.models.nivel.application.FindNivelByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ap/nivel")
@RequiredArgsConstructor
public class NivelFindByIdController {

	private final FindNivelByIdService findNivelByIdService;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Nivel getById(@PathVariable Long id) {
		return findNivelByIdService.findById(id);
	}

}
