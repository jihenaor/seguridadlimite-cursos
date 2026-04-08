package com.seguridadlimite.models.pregunta.application.findByNiveltipoevaluacion;

import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import com.seguridadlimite.models.pregunta.application.inicializarimagenes.InicializarImagenes;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarPreguntasNivelTipoEvaluacion {

	@Autowired
	private IPreguntaDao dao;

	@Autowired
	private InicializarImagenes inicializarImagenes;

	public List<Pregunta> findByNiveltipoevaluacion(Long idnivel, String tipoevaluacion) {
		List<Pregunta> l = dao.findByNiveltipoevaluacionOrden(idnivel, tipoevaluacion);

		inicializarImagenes.inicializarImagenes(l);

		return l;
	}
}
