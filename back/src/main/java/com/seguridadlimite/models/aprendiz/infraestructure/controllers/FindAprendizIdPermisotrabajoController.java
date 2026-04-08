package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.BuscarAprendizPorIdPermisoTrabajoService;
import com.seguridadlimite.models.aprendiz.domain.AprendizDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aprendiz")
@AllArgsConstructor
public class FindAprendizIdPermisotrabajoController {
	private BuscarAprendizPorIdPermisoTrabajoService buscarAprendizPorIdPermisoTrabajoService;

	@GetMapping("/{idPermiso}/permisotrabajo")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<AprendizDTO>> getByidpermiso(@PathVariable int idPermiso)  {
		return ResponseEntity.ok(buscarAprendizPorIdPermisoTrabajoService.findByidpermisotrabajo(idPermiso));
	}
}
