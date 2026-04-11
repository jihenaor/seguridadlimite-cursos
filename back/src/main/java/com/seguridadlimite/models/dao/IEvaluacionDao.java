package com.seguridadlimite.models.dao;

import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEvaluacionDao extends CrudRepository<Evaluacion, Long>{
	  @Query("select u"
		  		+ " from Evaluacion u"
		  		+ " where u.idaprendiz = ?1"
		  		+ " and u.idpregunta = ?2"
				+ " and u.numero = ?3")
	  Evaluacion findEvaluacion(Long idaprendiz,
								Long idpregunta,
								Integer numero);

	@Query("select u"
			+ " from Evaluacion u"
			+ " where u.pregunta.grupo.tipoevaluacion = ?1"
			+ " and u.idaprendiz = ?2"
			+ " and u.numero = ?3"
			+ " order by u.pregunta.orden")
	List<Evaluacion> findEvaluacionAprendiz(String tipoevaluacion,
											 int idaprendiz,
											 int numeroevaluacion);

	@Query("select u"
			+ " from Evaluacion u"
			+ " where u.pregunta.grupo.tipoevaluacion in ('T', 'E')"
			+ " and u.idaprendiz = ?1"
			+ " and u.numero = ?2"
			+ " order by  u.pregunta.orden")
	List<Evaluacion> findEvaluacionTeoricaAprendiz(Long idaprendiz,
											Integer numeroevaluacion);

	@Query("select u"
			+ " from Evaluacion u"
			+ " where u.pregunta.grupo.tipoevaluacion = ?2"
			+ " and u.idaprendiz = ?1"
			+ " order by u.pregunta.orden")
	List<Evaluacion> findEvaluacionTipo(
			Long idaprendiz,
			String tipoEvaluacion);

	@Query("select u"
			+ " from Evaluacion u"
			+ " where u.pregunta.grupo.tipoevaluacion in ?2"
			+ " and u.idaprendiz = ?1 and u.numero = ?3"
			+ " order by u.pregunta.grupo.tipoevaluacion, u.pregunta.orden")
	List<Evaluacion> findEvaluacionTipo(
			Long idaprendiz,
			List<String> tipoEvaluacion,
			Integer numero);

	@Query("SELECT u " +
			"FROM Evaluacion u " +
			"WHERE u.idaprendiz = :idaprendiz" +
			" and u.pregunta.grupo.tipoevaluacion = :tipoevaluacion")
	List<Evaluacion> findEvaluacionAprendiz(@Param("idaprendiz") Long idaprendiz,
											@Param("tipoevaluacion") String tipoevaluacion);

	@Query("select u"
			+ " from Evaluacion u"
			+ " where u.pregunta.grupo.tipoevaluacion = ?1"
			+ " and u.idaprendiz = ?2"
			+ " order by u.pregunta.grupo.id, u.pregunta.orden")
	List<Evaluacion> findEvaluacionPractica(String tipoevaluacion, int idaprendiz);

	@Query("select u"
			+ " from Evaluacion u"
			+ " where u.pregunta.grupo.tipoevaluacion = ?1"
			+ " and u.idaprendiz = ?2"
			+ " order by u.pregunta.grupo.id, u.pregunta.orden")
	List<Evaluacion> findEvaluacionPregunta(String tipoevaluacion, Long idaprendiz);

	@Modifying
	@Query("Delete from Evaluacion u"
			+ " where u.idpregunta in (select p.id from Pregunta p where p.grupo.tipoevaluacion = ?1)"
			+ " and u.idaprendiz = ?2")
	int deleteEvaluacionAprendizTipo(String tipoevaluacion, Long idaprendiz);
}
