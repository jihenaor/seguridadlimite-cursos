package com.seguridadlimite.models.parametros.application.UpdateEvaluationDate;

import com.seguridadlimite.models.parametros.dominio.Parametros;
import com.seguridadlimite.models.parametros.infraestructure.IParametrosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindParametrosById {

	@Autowired
	private IParametrosDao dao;

	@Transactional
	public Parametros find() {
		return dao.findById("1").orElseThrow();
	}
}
