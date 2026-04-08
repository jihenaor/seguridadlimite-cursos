package com.seguridadlimite.models.trabajador.infraestructure.controller;

import com.seguridadlimite.models.aprendiz.application.guardarFotoTrabajador.GuardarFotoTrabajadorService;
import com.seguridadlimite.models.entity.Fototrabajador;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@ResponseBody
@AllArgsConstructor
public class TrabajadorSaveFotoController extends Controller {
	private final GuardarFotoTrabajadorService service;

	@PostMapping(path = "/updateFoto", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> updateFoto(@RequestBody Fototrabajador fototrabajador) throws IOException {
		service.guardar(fototrabajador);
		return new ResponseEntity<>(
				"{\"status\" : \"ok\"}",
				HttpStatus.OK);

	}

}
