package com.seguridadlimite.springboot.backend.apirest.services;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.dao.IEmpresaDao;
import com.seguridadlimite.models.entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl {

	private final IEmpresaDao dao;
	
	@Transactional(readOnly = true)
	public List<Empresa> findAll() {
		List<Empresa> e =  (List<Empresa>) dao.findAll();
		
		e.add(0, new Empresa(0l, "0", "0", "Independiente"));
		
		return e;
	}

	@Transactional(readOnly = true)
	public List<Empresa> findSeleccionadas() {
		return (List<Empresa>) dao.findSeleccionada();
	}

	@Transactional(readOnly = true)
	public Empresa findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Empresa findByNumerodocumento(String numerodocumento) {
		return dao.findBynumerodocumento(numerodocumento);
	}
	
	@Transactional
	public Empresa save(Empresa entity) {
		return dao.save(entity);
	}

	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
