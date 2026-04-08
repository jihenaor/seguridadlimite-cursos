package com.seguridadlimite.models.asistencia.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class Modulo {
    private Integer modulo;

    private List<Dia> dias;

}
