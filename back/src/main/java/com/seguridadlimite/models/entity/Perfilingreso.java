package com.seguridadlimite.models.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Perfilingreso {
  private Long id;
  private String tipodocumento;
  private String numerodocumento;
  private String nombrecompleto;
  // Niveles
  private String trabajadorautorizado;
  private String nivelreentrenamiento;
  private String nivelbasicooperativo;
  private String actualizacioncoordinador;
  private String jefeareatrabajoalturas;
  //
  private String tipovinculacionlaboraldependiente;
  private String tipovinculacionlaboralindependiente;
  private String tipovinculacionlaboralninguno;


  private String regimenafiliacionseguridadsocialcontributivo;
  private String regimenafiliacionseguridadsocialsubsidiado;
  private String regimenafiliacionseguridadsocialotro;

  private String documentoidentidad;
  private String ultimopagoseguridadsocial;
  private String afiliacionseguridadsocial;
  private String certificadoaptitudmedica;

  private String hse;
  private String supervisor;

  private String embarazo;
  private String mesesgestacion;
  private String sourcefirma;

  private String reentrenamiento;
  private String actualizadocoordinador;


  public Perfilingreso() {
  }

  public Perfilingreso(Long id) {
    this.id = id;
  }

}
