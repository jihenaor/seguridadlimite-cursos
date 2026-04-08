package com.seguridadlimite.models.pregunta.infraestructure;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.seguridadlimite.models.pregunta.domain.Pregunta;

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

    @Query("select u from Pregunta u "
            + "where ((u.grupo.tipoevaluacion = 'T' and u.nivel.id = ?1)" +
            " or (u.grupo.tipoevaluacion = 'E' and u.idenfasis = ?2))" +
            " and u.diflectura = ?3"
            + " ORDER BY RAND()")
    List<Pregunta> findPreguntasEvaluacionTeorica(long idnivel,
                                                  long idenfasis,
                                                  String diflectura);

    @Query("select u from Pregunta u "
            + "where u.grupo.tipoevaluacion = ?1 " +
            "and u.diflectura = ?2"
            + " ORDER BY orden")
    List<Pregunta> findTipoevaluacion(String tipoEvaluacion, String diflectura);

    @Query("select u from Pregunta u"
            + " where u.nivel.id = ?1 and u.grupo.tipoevaluacion = ?2" +
            " ORDER by u.enfasis.id, u.agrupador1, u.orden")
    List<Pregunta> findByNiveltipoevaluacionOrden(Long idnivel, String tipoevaluacion);

    @Query("select u from Pregunta u "
            )
    List<Pregunta> findAllOrder();

    @Query("select u from Pregunta u "
            + "where u.grupo.id = ?1 "
)
    List<Pregunta> findByIdgrupo(Long idgrupo);

    @Query("select u from Pregunta u "
            + "where u.grupo.id = ?1 and u.nivel.id = ?2 ")
    List<Pregunta> findByIdgrupoIdnivel(Long idgrupo, Long idnivel);
}
