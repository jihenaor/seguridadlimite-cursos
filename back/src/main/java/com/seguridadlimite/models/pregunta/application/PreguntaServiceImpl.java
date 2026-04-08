package com.seguridadlimite.models.pregunta.application;

import com.seguridadlimite.models.pregunta.application.inicializarimagenes.InicializarImagenes;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PreguntaServiceImpl {

	@Autowired
	private IPreguntaDao dao;


	@Autowired
	private InicializarImagenes inicializarImagenes;

	@Transactional(readOnly = true)
	public List<Pregunta> findAll() {
		return dao.findAllOrder();
	}

	@Transactional(readOnly = true)
	public Pregunta findById(Long id) {
		return dao.findById(id).orElse(null);
	}


	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
