package com.seguridadlimite.models.evaluacion.application;

import com.seguridadlimite.models.dao.IEvaluacionDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteEvaluacionService {
	private IEvaluacionDao dao;

	@Transactional
	public void delete(String tipoevaluacion, Long idaprendiz) {
		dao.deleteEvaluacionAprendizTipo(tipoevaluacion, idaprendiz);
	}
}
