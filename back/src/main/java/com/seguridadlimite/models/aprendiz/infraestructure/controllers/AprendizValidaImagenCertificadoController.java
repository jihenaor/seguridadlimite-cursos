package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.validaExisteCertificado.extraerCertificado.ValidaExisteCertificadoTrabajadorService;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
	@RequestMapping("/api/aprendiz")
@RequiredArgsConstructor
public class AprendizValidaImagenCertificadoController {

	private final ValidaExisteCertificadoTrabajadorService service;

	@GetMapping("/validarcertificados")
	@ResponseStatus(HttpStatus.OK)
	public List<String> findImagenCertificado() throws BusinessException {
		return service.buscar();
	}
}
