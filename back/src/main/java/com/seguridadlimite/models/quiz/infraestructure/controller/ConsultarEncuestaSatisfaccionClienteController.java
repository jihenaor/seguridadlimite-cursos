package com.seguridadlimite.models.quiz.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.pojo.RespuestaEncuestaSatisfaccion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.quiz.application.ConsultarEncuestaSatisfaccionClienteService;
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
@RequiredArgsConstructor
public class ConsultarEncuestaSatisfaccionClienteController {

	private final ConsultarEncuestaSatisfaccionClienteService service;

	@GetMapping("/{idaprendiz}/satisfaccioncliente")
	public ResponseEntity<RespuestaEncuestaSatisfaccion> consultarevaluacionteorica(
			@PathVariable int idaprendiz) throws BusinessException {

		RespuestaEncuestaSatisfaccion respuestaEncuestaSatisfaccion = service.findPreguntasAprendiz(
					idaprendiz);

		return new ResponseEntity<>(respuestaEncuestaSatisfaccion, HttpStatus.OK);
	}
}
