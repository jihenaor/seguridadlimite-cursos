package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.findaprendizByIdWithDocumentAsistencia.FindAprendizByIdWithDocumentosAsistenciaService;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/aprendiz")
@ResponseBody
public class FindConDocumentosController extends Controller {

	@Autowired
	private FindAprendizByIdWithDocumentosAsistenciaService aprendizService;

	@GetMapping(path = "{id}/documentos")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Aprendiz> getByIdaprendiz(@PathVariable Long id) throws FileNotFoundException {
		return ResponseEntity.ok(aprendizService.find(id));
	}

}
