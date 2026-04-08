package com.seguridadlimite.models.dao;

import com.seguridadlimite.models.entity.Huella;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IHuellaDao extends CrudRepository<Huella, Long>{
  @Query("select u from Huella u where u.idtrabajador = ?1")
  Huella findByIdtrabajador(Long idtrabajador);
  /*
  @Query("select u"
  		+ " from Huella u "
  		+ "where u.idtrabajador in (Select a.trabajador.id"
  						+ " from Aprendiz a"
  						+ " where a.grupo.aucodestad = 'A')")
  List<Huella> findHuellasGruposActivos();

   */
}