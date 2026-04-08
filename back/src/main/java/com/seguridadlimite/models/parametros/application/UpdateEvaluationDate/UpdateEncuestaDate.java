package com.seguridadlimite.models.parametros.application.UpdateEvaluationDate;

import com.seguridadlimite.models.parametros.dominio.Parametros;
import com.seguridadlimite.models.parametros.infraestructure.IParametrosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
public class UpdateEncuestaDate {

	@Autowired
	private IParametrosDao dao;

	@Transactional
	public Parametros update() {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);

		Parametros parametros = dao.findById("1").orElseThrow();

		parametros.setFechaencuesta(calendar.getTime());
		dao.save(parametros);

		return parametros;
	}
}
