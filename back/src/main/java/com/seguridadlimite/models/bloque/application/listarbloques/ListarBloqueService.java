package com.seguridadlimite.models.bloque.application.listarbloques;

import com.seguridadlimite.models.bloque.infraestructure.IBloqueDao;
import com.seguridadlimite.models.bloque.model.Bloque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListarBloqueService {

	@Autowired
	private IBloqueDao dao;

	@Transactional(readOnly = true)
	public List<Bloque> findAll() {
		return (List<Bloque>) dao.findAll();
	}


}
