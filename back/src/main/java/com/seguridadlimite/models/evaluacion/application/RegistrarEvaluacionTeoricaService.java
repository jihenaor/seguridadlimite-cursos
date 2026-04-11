package com.seguridadlimite.models.evaluacion.application;

import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.*;
import com.seguridadlimite.models.pojo.RespuestaEvaluacion;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RegistrarEvaluacionTeoricaService {

	@Autowired
	private IEvaluacionDao dao;

	@Autowired
	private IAprendizDao aprendizDao;
	
	@Transactional
	public RespuestaEvaluacion saveevaluacion(List<Pregunta> entity, Long idaprendiz) throws Exception {
		Aprendiz aprendiz = null;

		int caprobadasTeorico = 0;
		int caprobadasEnfasis = 0;
		int contadorTeorico = 0;
		int contadorEnfasis = 0;

		if (idaprendiz == null ) {
			throw new Exception("No se ha seleccionado el aprendiz");
		}

		aprendiz = aprendizDao.findById(AprendizId.toInteger(idaprendiz)).orElse(null);

		for (Pregunta p : entity) {
			if (p.getGrupo().getTipoevaluacion().equals("T")) {
				contadorTeorico++;
			} else if (p.getGrupo().getTipoevaluacion().equals("E")) {
				contadorEnfasis++;
			}
			if (p.getIdevaluacion() == null) {
				throw new Exception(String.format("Error una pregunta %d sin id evaluacion", p.getId()));
			} else {
				actualizarEvaluacion(p);

				if (p.getRespuestacorrecta().equals("S")) {
					if (p.getGrupo().getTipoevaluacion().equals("T")) {
						caprobadasTeorico++;
					} else if (p.getGrupo().getTipoevaluacion().equals("E")) {
						caprobadasEnfasis++;
					}
				}
			}
		}

		if (contadorEnfasis == 0) {
			throw new BusinessException("La evaluación no incluye preguntas de tipo enfasis");
		}

		return actualizarEvaluacionAprendiz(idaprendiz,
				aprendiz,
				caprobadasTeorico,
				caprobadasEnfasis,
				contadorTeorico,
				contadorEnfasis);
	}

	private RespuestaEvaluacion actualizarEvaluacionAprendiz(Long idaprendiz,
															 Aprendiz aprendiz,
															 int caprobadasTeorico,
															 int caprobadasEnfasis,
															 int contadorTeorico,
															 int contadorEnfasis) throws Exception {
		double notaTeorico;
		double notaEnfasis;

		notaTeorico = (caprobadasTeorico / (double) contadorTeorico) * 5;
		notaEnfasis = (caprobadasEnfasis / (double) contadorEnfasis) * 5;

		try {
			if (aprendiz.getEteorica1() == 0) {
				aprendizDao.updateEvaluacionteorica1(notaTeorico, AprendizId.toInteger(idaprendiz));
			} else {
				aprendizDao.updateEvaluacionteorica2(notaTeorico, AprendizId.toInteger(idaprendiz));
			}


		} catch (Exception e) {

//			throw new Exception("Error actualizando evaluacion teorica del aprendiz " + idaprendiz + " " + e.getMessage());
			throw e;
		}

		aprendizDao.updateEvaluacionteoricaEnfasis(notaEnfasis, AprendizId.toInteger(idaprendiz));

		return new RespuestaEvaluacion(notaTeorico,
				notaEnfasis,
				caprobadasTeorico + caprobadasEnfasis,
				contadorTeorico + contadorEnfasis);
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

	@Transactional
	public void deleteAll() {
		dao.deleteAll();
	}


}
