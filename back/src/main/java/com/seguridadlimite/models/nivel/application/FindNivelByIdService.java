package com.seguridadlimite.models.nivel.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.nivel.infraestructure.INivelDao;
import com.seguridadlimite.models.nivel.domain.Nivel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindNivelByIdService {

	private final INivelDao dao;

	@Transactional(readOnly = true)
	public Nivel findById(Long id) {
		return dao.findById(id).orElse(null);
	}
}
