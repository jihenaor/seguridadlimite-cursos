package com.seguridadlimite.springboot.backend.apirest.services;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.dao.IHuellaDao;
import com.seguridadlimite.models.entity.Huella;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HuellaServiceImpl {

	private final IHuellaDao dao;
	
	@Transactional(readOnly = true)
	public List<Huella> findAll() {
//		return (List<Huella>) dao.findHuellasGruposActivos();
		return null;
	}

	@Transactional
	public Huella save(Huella t) {
		Huella huella;
		if (t.getId() == null || t.getIdtrabajador() == null) {
			huella = new Huella();
			huella.setIdtrabajador(t.getIdtrabajador());
		} else {
			if (t.getId() != null) {
				huella = dao.findById(t.getId()).orElse(null);				
			} else {
				huella = dao.findByIdtrabajador(t.getIdtrabajador());
			}
		}
		
		huella.setHuella(t.getHuella());
		
		dao.save(huella);
		
		return huella;
	}

	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
