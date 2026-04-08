package com.seguridadlimite.models.asistencia.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Dia {
    private int dia;
    private String fechaProgramada;
    private List<Unidad> unidads;
}
