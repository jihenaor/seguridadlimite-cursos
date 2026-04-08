package com.seguridadlimite.springboot.backend.apirest.services;

import com.seguridadlimite.iservices.INivelService;
import com.seguridadlimite.models.nivel.domain.Nivel;
import com.seguridadlimite.models.nivel.infraestructure.INivelDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class NivelrServiceImpl implements INivelService {


	private INivelDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<Nivel> findAll() {
		return (List<Nivel>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Nivel> findActivos() {
		return dao.findByEstado("A");
	}

	@Override
	@Transactional(readOnly = true)
	public Nivel findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Nivel save(Nivel entity) {
		return dao.save(entity);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
