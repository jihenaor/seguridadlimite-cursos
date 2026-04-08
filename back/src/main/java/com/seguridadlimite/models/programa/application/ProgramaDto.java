package com.seguridadlimite.models.programa.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;


@Getter
@AllArgsConstructor
public class ProgramaDto implements Serializable {
  private Long id;

  private String nombre;
}