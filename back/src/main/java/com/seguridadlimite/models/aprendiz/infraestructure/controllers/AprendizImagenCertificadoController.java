package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.extraerCertificado.ExtraerCertificadoTrabajadorService;
import com.seguridadlimite.models.entity.ReportePojo;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
	@RequestMapping("/api/aprendiz")
@RequiredArgsConstructor
public class AprendizImagenCertificadoController {

	private final ExtraerCertificadoTrabajadorService service;

	@GetMapping("/{id}/imagencertificado")
	@ResponseStatus(HttpStatus.OK)
	public ReportePojo findImagenCertificado(@PathVariable Long id) throws BusinessException {
		return new ReportePojo(service.buscar(id));
	}
}

