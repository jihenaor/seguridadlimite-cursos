package com.seguridadlimite.models.pregunta.application;

import com.seguridadlimite.models.entity.Respuesta;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SavePreguntaService {

	@Autowired
	private IPreguntaDao dao;

	@Transactional
	public Pregunta save(Pregunta pregunta) {
		pregunta.setAgrupador1(pregunta.getAgrupador1() == null ?
				"" : pregunta.getAgrupador1());

		if (pregunta.getId() == null) {
			pregunta = getNuevaPregunta(pregunta);
		}
		return dao.save(pregunta);
	}

	private Pregunta getNuevaPregunta(Pregunta pregunta) {
		pregunta.getRespuestas().forEach(respuesta -> respuesta.setIdpregunta(null));

		List<Respuesta> respuestas = pregunta.getRespuestas();
		pregunta.setRespuestas(null);
		pregunta = dao.save(pregunta);

		pregunta.setPregunta("(C) " + pregunta.getPregunta());

		Pregunta finalPregunta = pregunta;
		respuestas.forEach(respuesta -> respuesta.setIdpregunta(finalPregunta.getId()));
		pregunta.setRespuestas(respuestas);
		return pregunta;
	}
}
