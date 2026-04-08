package com.seguridadlimite.models.eps.application;

import com.seguridadlimite.models.eps.domain.Eps;
import com.seguridadlimite.models.eps.infraestructure.EpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EpsService implements IEpsService {

	@Autowired
	private EpsRepository dao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Eps> findAll() {
		return (List<Eps>) dao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Eps findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Eps save(Eps entity) {
		return dao.save(entity);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}	
}
