package com.seguridadlimite.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.seguridadlimite.models.entity.Solicitud;

public interface ISolicitudDao extends CrudRepository<Solicitud, Long>{
	@Query("select u from Solicitud u where u.aucodestad = ?1 order by u.id desc")
	List<Solicitud> findAll(String aucodestad);
}
