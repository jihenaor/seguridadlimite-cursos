package com.seguridadlimite.springboot.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seguridadlimite.iservices.IProgramaService;
import com.seguridadlimite.models.programa.infraestructure.IProgramaDao;
import com.seguridadlimite.models.programa.model.Programa;

@Service
public class ProgramaServiceImpl implements IProgramaService{

	@Autowired
	private IProgramaDao dao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Programa> findAll() {
		return (List<Programa>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Programa findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Programa save(Programa entity) {
		return dao.save(entity);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
