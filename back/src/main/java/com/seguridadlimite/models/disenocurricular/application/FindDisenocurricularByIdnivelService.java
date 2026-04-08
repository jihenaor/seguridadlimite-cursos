package com.seguridadlimite.models.disenocurricular.application;

import com.seguridadlimite.models.disenocurricular.domain.Disenocurricular;
import com.seguridadlimite.models.disenocurricular.infraestructure.IDisenocurricularDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindDisenocurricularByIdnivelService {

	@Autowired
	private IDisenocurricularDao dao;

	@Transactional(readOnly = true)
	public List<Disenocurricular> find(Long idnivel) {
		return dao.findByIdnivelOrderByDiaAsc(idnivel);
	}

	@Transactional
	public void save(Disenocurricular disenocurricular) {
		dao.save(disenocurricular);
	}
}
