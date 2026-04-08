package com.seguridadlimite.models.pojo;

import com.seguridadlimite.models.pregunta.domain.Pregunta;
import lombok.Getter;

import java.util.List;

@Getter
public class RespuestaEncuestaSatisfaccion {

    private String comentariosencuesta;

    private boolean activacomentarios;

    private List<Pregunta> preguntas;

    public RespuestaEncuestaSatisfaccion(String comentariosencuesta,
                                         boolean activacomentarios, List<Pregunta> preguntas) {
        this.comentariosencuesta = comentariosencuesta;
        this.activacomentarios = activacomentarios;
        this.preguntas = preguntas;
    }
}
