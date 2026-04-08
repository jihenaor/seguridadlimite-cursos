package com.seguridadlimite.models.documentos.application;

import com.seguridadlimite.models.dao.IDocumentoDao;
import com.seguridadlimite.models.documentos.domain.Documento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentoServiceImpl {

	@Autowired
	private IDocumentoDao dao;

	@Transactional(readOnly = true)
	public List<Documento> findAll() {
		return (List<Documento>) dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Documento> findByTipo(String tipo) {
		return (List<Documento>) dao.findByTipo(tipo);
	}
	
	@Transactional(readOnly = true)
	public List<Documento> findByDocumentosEmpresa() {
		return (List<Documento>) dao.findEmpresa();
	}

	@Transactional(readOnly = true)
	public Documento findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Transactional
	public Documento save(Documento entity) {
		return dao.save(entity);
	}

	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
