package com.seguridadlimite.models.evaluacion.infraestructura.controller;

import com.seguridadlimite.models.evaluacion.application.FindEvaluacionPracticaServiceImpl;
import com.seguridadlimite.models.grupopregunta.domain.GrupopreguntaDTO;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion")
public class FindEvaluacionPracticaController {

	@Autowired
	private FindEvaluacionPracticaServiceImpl service;

	@GetMapping("/{idaprendiz}/practica")
	public ResponseEntity<List<GrupopreguntaDTO>> evaluacionpractica(@PathVariable Long idaprendiz) throws BusinessException {
		List<GrupopreguntaDTO> l = service.findEvaluacionPracticaIdaprendiz(idaprendiz);

		 return new ResponseEntity<>(l, HttpStatus.OK);
	}
}
