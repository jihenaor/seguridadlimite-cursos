package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class FindConDocumentosController extends Controller {

	private final FindAprendizByIdWithDocumentosAsistenciaService aprendizService;

	/**
	 * id = identificador del aprendiz (no del trabajador). Incluye documentos tipo "A" y, vía
	 * {@link com.seguridadlimite.models.trabajador.application.EncodeFoto.EncodeFotoTrabajadorService},
	 * deja {@code trabajador.foto} en S/N según exista archivo F{idTrabajador} en disco (y rellena base64 de la foto).
	 */
	@GetMapping(path = "{id}/documentos")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Aprendiz> getByIdaprendiz(@PathVariable Long id) throws FileNotFoundException {
		return ResponseEntity.ok(aprendizService.find(id));
	}

}
