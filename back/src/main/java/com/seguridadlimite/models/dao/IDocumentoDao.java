package com.seguridadlimite.models.dao;

import com.seguridadlimite.models.documentos.domain.Documento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IDocumentoDao extends CrudRepository<Documento, Long>{
  @Query("select u from Documento u where u.tipo = ?1")
	  List<Documento> findByTipo(String tipo);
  
  @Query("select u from Documento u where u.id > 2")
  List<Documento> findEmpresa();

}
