package com.seguridadlimite.models.nivel.application.findInscripcionesAbiertas;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.nivel.domain.Nivel;
import com.seguridadlimite.models.nivel.infraestructure.INivelDao;
import com.seguridadlimite.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindInscripcionesAbiertasCu {

	private final INivelDao dao;

	@Transactional
	public List<Nivel> validar()  {
		return dao.findInscripcionesAbiertas(DateUtil.getCurrentDate());
	}
}
