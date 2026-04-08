package com.seguridadlimite.models.aprendiz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class Pagopendienteempresa implements Serializable {

  private String empresa;
  private String nit;

  private LocalDate fechainscripcion;

  private List<Aprendiz> aprendizs;
}
