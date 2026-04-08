package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.BuscarAprendizUltimaAsistenciaCoincidePorIdPermisoTrabajoService;
import com.seguridadlimite.models.aprendiz.domain.AprendizDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aprendiz")
@AllArgsConstructor
public class FindAprendizUltimaAsistenciaIdPermisotrabajoController {
	private BuscarAprendizUltimaAsistenciaCoincidePorIdPermisoTrabajoService buscarAprendizUltimaAsistenciaCoincidePorIdPermisoTrabajoService;

	@GetMapping("/{idPermiso}/permisotrabajo/ultimaasistencia")
	@ResponseStatus(HttpStatus.OK)
	public List<AprendizDTO> getByidpermiso(@PathVariable int idPermiso)  {
		return buscarAprendizUltimaAsistenciaCoincidePorIdPermisoTrabajoService.findByidpermisotrabajo(idPermiso);
	}

	
}
