package com.seguridadlimite.models.dao;

import com.seguridadlimite.models.trabajador.dominio.Trabajador;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITrabajadorDao extends CrudRepository<Trabajador, Long>{
  @Query("select u from Trabajador u where u.numerodocumento = ?1")
  Trabajador findBynumerodocumento(String numerodocumento);
  
  @Query("select u from Trabajador u where trim(concat(u.primernombre, ' ', segundonombre, ' ', primerapellido, ' ', segundoapellido)) like ?1")
  List<Trabajador> findBynombre(String nombre);
/*
  @Query("select u"
  		+ " from Trabajador u "
  		+ "where u.id in (Select a.idtrabajador from AprendizSinRelaciones a where a.idgrupo = ?1)")
  List<Trabajador> findByIdgrupo(Long idgrupo);
  */
    /*
  @Query("select u"
	  		+ " from Trabajador u "
	  		+ "where u.id in (Select a.trabajador.id"
  							+ " from Aprendiz a where a.grupo.aucodestad = 'A')")
	  List<Trabajador> findGruposActivos();
*/
  @Modifying
  @Query("UPDATE Trabajador t SET t.foto = :foto WHERE t.id = :idTrabajador")
  void actualizarFoto(@Param("idTrabajador") Long idTrabajador, @Param("foto") String foto);

}