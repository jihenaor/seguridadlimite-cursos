package com.seguridadlimite.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.seguridadlimite.models.documentoaprendiz.domain.Documentoaprendiz;

import java.util.List;

public interface IDocumentoaprendizDao extends CrudRepository<Documentoaprendiz, Long>{
	@Query("select u from Documentoaprendiz u where u.iddocumento = ?1 and u.idaprendiz = ?2")
	Documentoaprendiz findByIdDocumentoIdaprendiz(Long iddocumento, Long idaprendiz);

	List<Documentoaprendiz> findByIdaprendiz(Long idaprendiz);
}

