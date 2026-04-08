package com.seguridadlimite.models.grupopregunta.infraestructure;

import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IGrupopreguntaDao extends CrudRepository<Grupopregunta, Long>{
    @Query("select u"
            + " from Grupopregunta u"
            + " where tipoevaluacion = ?1"
            + " order by orden")
    List<Grupopregunta> findEvaluacionAprendiz(String tipoevaluacion);
}
