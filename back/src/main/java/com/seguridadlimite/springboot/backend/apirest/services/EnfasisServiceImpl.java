package com.seguridadlimite.springboot.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seguridadlimite.models.dao.IEnfasisDao;
import com.seguridadlimite.models.enfasis.domain.Enfasis;

@Service
public class EnfasisServiceImpl {

	@Autowired
	private IEnfasisDao dao;
	
	@Transactional(readOnly = true)
	public List<Enfasis> findAll() {
		return (List<Enfasis>) dao.findAll();
	}

	@Transactional(readOnly = true)
	public Enfasis findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Transactional
	public Enfasis save(Enfasis entity) {
		return dao.save(entity);
	}

	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
