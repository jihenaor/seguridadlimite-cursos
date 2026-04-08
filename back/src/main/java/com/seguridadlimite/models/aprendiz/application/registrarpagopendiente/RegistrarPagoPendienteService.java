package com.seguridadlimite.models.aprendiz.application.registrarpagopendiente;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.Pagopendienteempresa;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarPagoPendienteService {

	@Autowired
	private IAprendizDao dao;

	@Transactional
	public void register(Pagopendienteempresa pagopendienteempresa) {
		for (Aprendiz aprendiz : pagopendienteempresa.getAprendizs()) {
			updateAprendiz(aprendiz);
		}
	}

	@Transactional
	public void updateAprendiz(Aprendiz aprendiz) {
		dao.updatePagoCurso("S", aprendiz.getId());
	}

}
