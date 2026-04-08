package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.inscribiraprendiz.InscribirAprendiz;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.entity.TrabajadorInscripcionPojo;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprendiz")
@ResponseBody
public class InscripcionController extends Controller {

	private InscribirAprendiz inscribirAprendiz;

	@Autowired
	public InscripcionController(InscribirAprendiz inscribirAprendiz) {
		this.inscribirAprendiz = inscribirAprendiz;
	}

	@PostMapping(path = "/save")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Aprendiz> createTrabajadoraprendiz(
			@RequestBody TrabajadorInscripcionPojo trabajadorInscripcionPojo)  {

		return ResponseEntity.ok(inscribirAprendiz.inscribir(trabajadorInscripcionPojo));
	}

	@PostMapping(path = "/savelector")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Aprendiz> createTrabajadoraprendizlector(
			@RequestBody TrabajadorInscripcionPojo trabajadorInscripcionPojo)  {

		return ResponseEntity.ok(inscribirAprendiz.inscribir(trabajadorInscripcionPojo));
	}
}
