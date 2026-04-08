package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.BuscarAprendizInscritosPorIdPermisoTrabajoService;
import com.seguridadlimite.models.aprendiz.domain.AprendizDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aprendiz")
@AllArgsConstructor
public class FindAprendizInscritosIdPermisotrabajoController {
	private BuscarAprendizInscritosPorIdPermisoTrabajoService buscarAprendizInscritosPorIdPermisoTrabajoService;

	@GetMapping("/{idPermiso}/permisotrabajo/inscritos")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<AprendizDTO>> getByidpermiso(@PathVariable int idPermiso)  {
		return ResponseEntity.ok(buscarAprendizInscritosPorIdPermisoTrabajoService.findByidpermisotrabajo(idPermiso));
	}
}
