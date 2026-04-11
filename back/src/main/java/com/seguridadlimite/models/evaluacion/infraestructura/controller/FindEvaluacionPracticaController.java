package com.seguridadlimite.models.evaluacion.infraestructura.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.evaluacion.application.FindEvaluacionPracticaServiceImpl;
import com.seguridadlimite.models.grupopregunta.domain.GrupopreguntaDTO;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion")
@RequiredArgsConstructor
public class FindEvaluacionPracticaController {

	private final FindEvaluacionPracticaServiceImpl service;

	@GetMapping("/{idaprendiz}/practica")
	public ResponseEntity<List<GrupopreguntaDTO>> evaluacionpractica(@PathVariable int idaprendiz) throws BusinessException {
		List<GrupopreguntaDTO> l = service.findEvaluacionPracticaIdaprendiz(idaprendiz);

		 return new ResponseEntity<>(l, HttpStatus.OK);
	}
}
