package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.registrarpagopendiente.RegistrarPagoPendienteService;
import com.seguridadlimite.models.aprendiz.domain.Pagopendienteempresa;
import com.seguridadlimite.models.entity.RespuestaWs;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprendiz")
@ResponseBody
@RequiredArgsConstructor
public class RegistrarPagosPendientesController extends Controller {

	private final RegistrarPagoPendienteService service;

	@PostMapping(path = "/registrarpagopendienteempresa")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RespuestaWs> ConfirmarPagoPendiente(
			@RequestBody Pagopendienteempresa pagopendienteempresa
	) throws BusinessException {
		service.register(pagopendienteempresa);

		return ResponseEntity.ok(new RespuestaWs());
	}
}
