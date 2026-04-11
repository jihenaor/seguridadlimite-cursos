package com.seguridadlimite.models.grupopregunta.application.listargrupopregunta;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;
import com.seguridadlimite.models.grupopregunta.infraestructure.IGrupopreguntaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoPreguntaService {

	private final IGrupopreguntaDao dao;
	

	@Transactional(readOnly = true)
	public List<Grupopregunta> findAll() {
		return (List<Grupopregunta>) dao.findAll();
	}


}
