package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.UpdateIdPermisoAprendizService;
import com.seguridadlimite.models.entity.RespuestaWs;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprendiz")
public class AprendizUpdateIdPermisoController {

	@Autowired
	private UpdateIdPermisoAprendizService service;

	@PostMapping("/updateIdpermiso")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RespuestaWs> updatePerfil(
			@RequestBody UpdateIdPermisoRequest request) throws BusinessException {
		service.update(request.getIdAprendiz(), request.getIdPermiso());
		return new ResponseEntity<RespuestaWs>(new RespuestaWs(), HttpStatus.OK);
	}
}

@Data
class UpdateIdPermisoRequest {
	private long idAprendiz;
	private int idPermiso;

}