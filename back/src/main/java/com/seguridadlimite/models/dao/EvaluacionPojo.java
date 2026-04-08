package com.seguridadlimite.models.dao;

import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EvaluacionPojo {
	private List<Evaluacion> evaluacions;
	
	private List<Pregunta> preguntas;

}
