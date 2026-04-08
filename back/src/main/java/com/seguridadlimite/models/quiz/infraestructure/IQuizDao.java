package com.seguridadlimite.models.quiz.infraestructure;


import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.quiz.infraestructure.projection.TotalesEncuestaSatisfaccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IQuizDao extends CrudRepository<Pregunta, Long>{
    @Query(value = "select sa.anoencuesta as ano, " +
            "sa.mesencuesta as mes, " +
            "sp.pregunta as pregunta, " +
            "se.respuestacorrecta respuesta, " +
            "count(*) as contador " +
            "from sl_evaluaciones se inner join sl_preguntas sp on se.idpregunta = sp.id " +
            "INNER join sl_aprendices sa on sa.id = se.idaprendiz " +
            "INNER join sl_grupospregunta sg on sg.id = sp.idgrupo " +
            "where sg.tipoevaluacion = 'C' " +
            "group by sa.anoencuesta, sa.mesencuesta, sp.pregunta, se.respuestacorrecta",
    nativeQuery = true)
    List<TotalesEncuestaSatisfaccion> find();


}
