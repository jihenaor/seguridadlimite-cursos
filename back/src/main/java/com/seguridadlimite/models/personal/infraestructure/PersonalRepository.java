package com.seguridadlimite.models.personal.infraestructure;

import com.seguridadlimite.models.personal.dominio.Personal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonalRepository extends CrudRepository<Personal, Long>{
  @Query("select u from Personal u where u.numerodocumento = ?1")
  Personal findBynumerodocumento(String numerodocumento);
  
  @Query("select u"
  		+ " from Personal u"
  		+ " where u.numerodocumento = ?1 or u.email = ?1")
  Personal findBylogin(String login);
  
  @Query("select u"
	  		+ " from Personal u"
	  		+ " where (u.numerodocumento = ?1 or u.email = ?1) and u.password = ?2")
	  Personal findBylogin(String login, String password);
  
  @Query("select u"
	  		+ " from Personal u"
	  		+ " where u.entrenador = 'S'")
  List<Personal> findInstructores();
  
  @Query("select u"
	  		+ " from Personal u"
	  		+ " where u.supervisor = 'S'")
  List<Personal> findSupervisor();
}
