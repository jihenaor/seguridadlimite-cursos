package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.registraContinuacionAprendizaje.findTrabajadorInscripcion.RegistraContinuacionAprendizajeCu;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/aprendiz")
@AllArgsConstructor
public class RegistrarContinuarAprendizajeController extends Controller {

	private final RegistraContinuacionAprendizajeCu registraContinuacionAprendizajeCu;

	@PostMapping(path = "/continuaaprendizaje")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> continuaaprendizaje(@RequestBody long idAprendiz) {
		registraContinuacionAprendizajeCu.registrarContinuaAprendizaje(idAprendiz);
		return ResponseEntity.noContent().build(); // Retorna un 204 sin cuerpo
	}

}
