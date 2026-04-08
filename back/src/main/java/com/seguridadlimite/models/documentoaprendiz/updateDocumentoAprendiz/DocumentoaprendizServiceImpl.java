package com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz;

import com.seguridadlimite.models.dao.IDocumentoaprendizDao;
import com.seguridadlimite.models.documentoaprendiz.domain.Documentoaprendiz;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentoaprendizServiceImpl {


	private IDocumentoaprendizDao dao;
	
	@Transactional(readOnly = true)
	public List<Documentoaprendiz> findAll() {
		return (List<Documentoaprendiz>) dao.findAll();
	}

	@Transactional(readOnly = true)
	public Documentoaprendiz findById(Long id) {
		return dao.findById(id).orElse(null);
	}
	
	@Transactional
	public void deletAll() {
		dao.deleteAll();
	}
	
	@Transactional(readOnly = true)
	public Documentoaprendiz findByIdAprendiz(Long iddocumento, Long idapendiz) {
		return dao.findByIdDocumentoIdaprendiz(iddocumento, idapendiz);
	}

	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
}

