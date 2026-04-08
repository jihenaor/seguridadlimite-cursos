package com.seguridadlimite.models.trabajador.infraestructure.controller;

import com.seguridadlimite.models.entity.RespuestaWs;
import com.seguridadlimite.models.trabajador.application.TrabajadorSaveCu;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trabajador")
@AllArgsConstructor
public class TrabajadorUpdateController {

	private TrabajadorSaveCu trabajadorSaveCu;

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RespuestaWs> updatePerfil(@RequestBody Trabajador entity, @PathVariable Long id) throws BusinessException {
		trabajadorSaveCu.save(entity);
		return new ResponseEntity<RespuestaWs>(new RespuestaWs(), HttpStatus.OK);
	}
}
