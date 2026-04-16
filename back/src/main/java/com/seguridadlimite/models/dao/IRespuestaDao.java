package com.seguridadlimite.models.dao;

import com.seguridadlimite.models.entity.Respuesta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IRespuestaDao extends CrudRepository<Respuesta, Long> {

	@Modifying(clearAutomatically = true)
	@Query("delete from Respuesta r where r.idpregunta = :idPregunta")
	int deleteByIdpregunta(@Param("idPregunta") Long idPregunta);
}
