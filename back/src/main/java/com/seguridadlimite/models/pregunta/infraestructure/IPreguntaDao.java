package com.seguridadlimite.models.pregunta.infraestructure;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.seguridadlimite.models.pregunta.domain.Pregunta;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPreguntaDao extends CrudRepository<Pregunta, Long>{
    @Query("select u from Pregunta u "
            + "where u.nivel.id = ?1 " +
               "and u.grupo.tipoevaluacion = ?2 " +
            " and u.diflectura = ?3" +
            "ORDER BY RAND()")
    List<Pregunta> findByNiveltipoevaluacionRandom(
            Long idnivel,
            String tipoevaluacion,
            String diflectura);

    /**
     * Evaluación teórica: bloques 'T' por nivel; bloques 'E' con énfasis en {@code idsEnfasis}
     * (p. ej. {@code 0} genérico y el del aprendiz).
     */
    @Query("select u from Pregunta u "
            + "where ((u.grupo.tipoevaluacion = 'T' and u.nivel.id = :idnivel) "
            + "or (u.grupo.tipoevaluacion = 'E' and u.idenfasis in :idsEnfasis)) "
            + "and u.diflectura = :diflectura "
            + "ORDER BY RAND()")
    List<Pregunta> findPreguntasEvaluacionTeorica(
            @Param("idnivel") long idnivel,
            @Param("idsEnfasis") List<Long> idsEnfasis,
            @Param("diflectura") String diflectura);

    @Query("select u from Pregunta u "
            + "where u.grupo.tipoevaluacion = ?1 " +
            "and u.diflectura = ?2"
            + " ORDER BY orden")
    List<Pregunta> findTipoevaluacion(String tipoEvaluacion, String diflectura);

    /**
     * Ingreso ({@code numeroevaluacion = 0}): preguntas sin énfasis definido ({@code null} o id 0)
     * o cuyo énfasis está entre los permitidos (p. ej. el del aprendiz).
     */
    @Query("select u from Pregunta u "
            + "where u.grupo.tipoevaluacion = :tipo "
            + "and u.diflectura = :diflectura "
            + "and (coalesce(u.idenfasis, 0) = 0 or u.idenfasis in :idsEnfasis) "
            + "order by u.orden")
    List<Pregunta> findTipoevaluacionIngresoPorEnfasis(
            @Param("tipo") String tipoEvaluacion,
            @Param("diflectura") String diflectura,
            @Param("idsEnfasis") List<Long> idsEnfasis);

    @Query("select u from Pregunta u"
            + " where u.nivel.id = ?1 and u.grupo.tipoevaluacion = ?2" +
            " ORDER by u.enfasis.id, u.agrupador1, u.orden")
    List<Pregunta> findByNiveltipoevaluacionOrden(Long idnivel, String tipoevaluacion);

    @Query("select u from Pregunta u "
            )
    List<Pregunta> findAllOrder();

    @Query("select distinct u from Pregunta u left join fetch u.respuestas "
            + "where u.grupo.id = ?1 order by u.orden")
    List<Pregunta> findByIdgrupo(Long idgrupo);

    @Query("select distinct u from Pregunta u left join fetch u.respuestas "
            + "where u.grupo.id = ?1 and u.nivel.id = ?2 order by u.orden")
    List<Pregunta> findByIdgrupoIdnivel(Long idgrupo, Long idnivel);
}
