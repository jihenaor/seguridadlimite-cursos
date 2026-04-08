package com.seguridadlimite.models.parametros.infraestructure.controller;

import com.seguridadlimite.models.aprendiz.application.ActualizarFechaLimiteEvaluacion;
import com.seguridadlimite.models.parametros.application.UpdateEvaluationDate.UpdateEvaluationDate;
import com.seguridadlimite.models.parametros.dominio.Parametros;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/parametros")
public class UpdateEvaluationDateController extends Controller {

	@Autowired
	private UpdateEvaluationDate service;


	@Autowired
	private ActualizarFechaLimiteEvaluacion actualizarFechaLimiteEvaluacion;


	@PostMapping(path = "/updateEvaluationDate", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Parametros> updateEvaluationDate() throws BusinessException {

		Parametros parametros = service.update();

		Date fechaEvaluacion = parametros.getFechaevaluacion();

		actualizarFechaLimiteEvaluacion.update(DateUtil.dateToString(fechaEvaluacion, "YYYY-MM-DD"));

		return ResponseEntity.ok(parametros);
	}
}
