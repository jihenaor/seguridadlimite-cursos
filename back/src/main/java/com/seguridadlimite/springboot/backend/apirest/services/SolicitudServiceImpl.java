package com.seguridadlimite.springboot.backend.apirest.services;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seguridadlimite.models.dao.ISolicitudDao;
import com.seguridadlimite.models.entity.Solicitud;

@Service
@RequiredArgsConstructor
public class SolicitudServiceImpl{

	private final ISolicitudDao dao;

	@Transactional(readOnly = true)
	public List<Solicitud> findAll(String aucodestad) {
		return (List<Solicitud>) dao.findAll(aucodestad);
	}
	
	@Transactional
	public void deletAll() {
		dao.deleteAll();
	}

	@Transactional
	public Solicitud save(Solicitud entity) {
		
		if (entity.getAucodestad() == null) {
			entity.setAucodestad("A");
		}
		if (entity.getCreateAt() == null) {
			entity.setCreateAt(new Date());
		}
		if (entity.getUpdateAt() == null) {
			entity.setUpdateAt(new Date()	);
		}
		return dao.save(entity);
	}
	

	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
	
}
