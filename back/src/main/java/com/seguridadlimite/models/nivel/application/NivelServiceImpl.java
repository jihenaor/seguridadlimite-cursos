package com.seguridadlimite.models.nivel.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.nivel.infraestructure.INivelDao;
import com.seguridadlimite.models.nivel.domain.Nivel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelServiceImpl {

	private final INivelDao dao;

	@Transactional(readOnly = true)
	public List<Nivel> findAll() {
		return (List<Nivel>) dao.findAll();
	}


	@Transactional
	public Nivel save(Nivel entity) {
		return dao.save(entity);
	}

}
