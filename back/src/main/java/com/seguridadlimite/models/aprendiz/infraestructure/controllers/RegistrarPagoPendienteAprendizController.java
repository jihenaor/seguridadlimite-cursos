package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.registrarpagopendiente.RegistrarPagoPendienteService;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
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
public class RegistrarPagoPendienteAprendizController extends Controller {

	@Autowired
	private RegistrarPagoPendienteService service;

	@PostMapping(path = "/registrarpagopendienteaprendiz")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RespuestaWs> ConfirmarPagoPendiente(
			@RequestBody Aprendiz aprendiz
	) throws BusinessException {
		service.updateAprendiz(aprendiz);

		return ResponseEntity.ok(new RespuestaWs());
	}
}
