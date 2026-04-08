package com.seguridadlimite.models.evaluacion.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pojo.RespuestaEvaluacion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrarEvaluacionIngresoService {

	@Autowired
	private IEvaluacionDao dao;

	@Autowired
	private IAprendizDao aprendizDao;
	
	@Transactional
	public RespuestaEvaluacion saveevaluacion(List<Pregunta> entity, Long idaprendiz) throws Exception {
		Aprendiz aprendiz;

		int caprobadas = 0;
		int contador = 0;

		if (idaprendiz == null ) {
			throw new Exception("No se ha seleccionado el aprendiz");
		}

		aprendiz = aprendizDao.findById(idaprendiz).orElse(null);

		for (Pregunta p : entity) {
			contador++;

			if (p.getIdevaluacion() == null) {
				throw new Exception(String.format("Error una pregunta %d sin id evaluacion", p.getId()));
			} else {
				actualizarEvaluacion(p);

				if (p.getRespuestacorrecta().equals("S")) {
					caprobadas++;
				}
			}
		}

		return actualizarEvaluacionAprendiz(idaprendiz, caprobadas, contador);
	}

	private RespuestaEvaluacion actualizarEvaluacionAprendiz(Long idaprendiz,
															 int caprobadas,
															 int contador) throws Exception {
		double nota;

		nota = (caprobadas / (double) contador) * 5;

		try {
			aprendizDao.updateEvaluacionIngreso(nota, idaprendiz);
		} catch (Exception e) {
			throw new Exception("Error actualizando evaluacion teorica del aprendiz " + idaprendiz + " " + e.getMessage());
		}

		return new RespuestaEvaluacion(nota,
				0,
				caprobadas ,
0);
	}

	private void actualizarEvaluacion(Pregunta p) {
		Evaluacion e = dao.findById(p.getIdevaluacion()).orElse(null);

		if (e != null) {
			e.setRespuestacorrecta(p.getRespuestacorrecta());
			e.setNumerorespuesta(p.getNumerorespuesta());
			e.setNumerorespuesta(p.getNumerorespuesta());
			e.setTextorespuesta(p.getTextorespuesta());
			dao.save(e);
		}
	}
}
