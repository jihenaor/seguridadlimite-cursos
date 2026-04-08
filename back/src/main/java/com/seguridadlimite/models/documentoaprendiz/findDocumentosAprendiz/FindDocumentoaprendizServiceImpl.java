package com.seguridadlimite.models.documentoaprendiz.findDocumentosAprendiz;

import com.seguridadlimite.models.dao.IDocumentoaprendizDao;
import com.seguridadlimite.models.documentoaprendiz.domain.Documentoaprendiz;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FindDocumentoaprendizServiceImpl {


	private IDocumentoaprendizDao dao;
	

	@Transactional(readOnly = true)
	public List<Documentoaprendiz> findByIdAprendiz(Long idapendiz) {
		return dao.findByIdaprendiz(idapendiz);
	}
}

