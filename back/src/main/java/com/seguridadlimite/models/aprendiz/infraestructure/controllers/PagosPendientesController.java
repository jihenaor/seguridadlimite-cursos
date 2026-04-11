package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.informpagospendientes.GeneratePendingPaymentReport;
import com.seguridadlimite.models.aprendiz.domain.Pagopendienteempresa;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aprendiz")
@ResponseBody
@RequiredArgsConstructor
public class PagosPendientesController extends Controller {

	private final GeneratePendingPaymentReport service;

	@GetMapping(path = "/pagopendienteempresa", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Pagopendienteempresa>> find() throws BusinessException {
		return ResponseEntity.ok(service.find());
	}
}
