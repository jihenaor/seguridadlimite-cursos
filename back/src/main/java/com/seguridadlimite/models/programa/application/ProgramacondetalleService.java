package com.seguridadlimite.models.programa.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.personal.infraestructure.PersonalRepository;
import com.seguridadlimite.models.programa.infraestructure.IProgramaDao;
import com.seguridadlimite.models.programa.model.Programa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramacondetalleService {

	private final IProgramaDao dao;

	private final PersonalRepository personalDao;

	@Transactional(readOnly = true)
	public List<Programa> findAll() {
		return (List<Programa>) dao.findAll();
	}

	@Transactional(readOnly = true)
	public List<ProgramaDto> findAllCursosActivos() {
		List<Programa> programas = (List<Programa>) dao.findAll();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		int dia = calendar.get(Calendar.DAY_OF_MONTH);

		if (dia <= 15) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-15);
		} else {
			calendar.set(Calendar.DAY_OF_MONTH,1);
		}

		return null;

	}

	@Transactional(readOnly = true)
	public Programa findById(Long id) {
		return dao.findById(id).orElse(null);
	}

}
