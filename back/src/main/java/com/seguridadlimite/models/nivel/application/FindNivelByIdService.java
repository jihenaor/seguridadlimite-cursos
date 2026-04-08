package com.seguridadlimite.models.nivel.application;

import com.seguridadlimite.models.nivel.infraestructure.INivelDao;
import com.seguridadlimite.models.nivel.domain.Nivel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindNivelByIdService {

	@Autowired
	private INivelDao dao;

	@Transactional(readOnly = true)
	public Nivel findById(Long id) {
		return dao.findById(id).orElse(null);
	}
}
