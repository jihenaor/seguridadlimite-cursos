package com.seguridadlimite.springboot.backend.apirest.services;

import com.seguridadlimite.iservices.IGrupopreguntaService;
import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;
import com.seguridadlimite.models.grupopregunta.infraestructure.IGrupopreguntaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupopreguntaServiceImpl implements IGrupopreguntaService {

	@Autowired
	private IGrupopreguntaDao dao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Grupopregunta> findAll() {
		return (List<Grupopregunta>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Grupopregunta findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Grupopregunta save(Grupopregunta entity) {
		return dao.save(entity);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}	
}
