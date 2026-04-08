package com.seguridadlimite.models.evaluacion.application;

import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;
import com.seguridadlimite.shared.domain.Constants;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.exceptions.DataBaseExecutionException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SaveEvaluacionPracticaService {

	private final IEvaluacionDao dao;

	private final IAprendizDao aprendizDao;

	@Transactional
	public void saveEvaluacionPractica(List<Grupopregunta> entity)  {
		long idaprendiz;
		double nota;
		int caprobadas = 0;
		int contador = 0;

		if (entity == null || entity.isEmpty() || entity.get(0).getEvaluacions().isEmpty()) {
			throw new BusinessException("Lista de evaluaciones vacía o nula");
		}

		idaprendiz = entity.get(0).getEvaluacions().get(0).getIdaprendiz();

		for (com.seguridadlimite.models.grupopregunta.domain.Grupopregunta grupo: entity) {
			for (Evaluacion evaluacion : grupo.getEvaluacions()) {

				Evaluacion ev = dao.findById(evaluacion.getId())
						.orElseThrow(() -> new BusinessException("Aprendiz " + idaprendiz + ".  Evaluación con ID " + evaluacion.getId() + " no encontrada"));

				ev.setNumerorespuesta(evaluacion.getNumerorespuesta());
				ev.setRespuestacorrecta(evaluacion.getRespuestacorrecta());

				if (!Constants.RESPUESTA_NO_APLICA.equals(evaluacion.getRespuestacorrecta())) {
					contador++;
				}

				dao.save(ev);

				if (evaluacion.getRespuestacorrecta().equals("S")) {
					caprobadas++;
				}
			}
		}

		if (contador == 0) {
			throw new BusinessException("La evaluación no es valida, el aprendiz no aplica a nínguna de las preguntas");
		}

		if (contador > 0) {
			nota = ((double) caprobadas / contador) * 5;
		} else {
			nota = 0;
		}

		try {
			aprendizDao.updateEvaluacionpractica(nota, idaprendiz);
		} catch (Exception e) {
			throw new DataBaseExecutionException("Error actualizando nota " + nota + " del aprendiz " + idaprendiz, e);
		}
	}
}
