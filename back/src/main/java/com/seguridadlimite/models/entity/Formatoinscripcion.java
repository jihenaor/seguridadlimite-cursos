package com.seguridadlimite.models.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Formatoinscripcion {
  private Long id;
  private String tipodocumento;
  private String numerodocumento;
  private String primernombre;
  private String segundonombre;
  private String primerapellido;
  private String segundoapellido;
  private String nombrecompleto;
  private String niveleducativoprimaria;
  private String niveleducativobachillerato;
  private String niveleducativotecnico;
  private String niveleducativotecnologo;
  private String niveleducativouniversitaria;
  private String cargoactual;
  private String genero;
  private String nacionalidad;
  private String tiposangre;
  private String ocupacion;
  private String departamentodomicilio;
  private String ciudaddomicilio;
  private String direcciondomicilio;
  private String celular;
  private String correoelectronico;
  private String eps;
  private String arl;
  private String sabeleerescribir;
  private String labordesarrolla;
  private String otralabor;
  private String siso;
  private String operativa;
  private String administrativojefearea;
  private String embarazo;
  private String mesesgestacion;
  private String alergias;
  private String medicamentos;
  private String enfermedades;
  private String lesiones;
  private String drogas;
  private String nombrecontacto;
  private String telefonocontacto;
  private String parentescocontacto;
  private String trabajadorautorizado;
  private String nivelcoordinador;
  private String nivelbasicooperativo;
  private String nivelreentrenamiento;
  private String actualizacioncoordinador;
  private String nivelotro;
  private String hidrocarburos;
  private String telecomunicaciones;
  private String construccion;
  private String electrico;
  private String industrial;
  private String otros;
  private String dia;
  private String mes;
  private String ano;
  private String firmaaprendiz;
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

  private String certificadotrabajadorautorizado;
  private String certificadonivelcoordinador;

  private String empresa;

  private String conceptoapto;
  private String certificadovigente;
  private String eingresoexcelente;
  private String eingresobueno;

  private String supervisor;
  private String noaplica;
  private String otro;
  private String copiapagoseguridadsocial;

  private String subsidiado;
  private String cual;
  private String copiadocumentoidentidad;
  private String afiliciacionseguridadsocial;
  private String reentrenamiento;
  private String actualizadocoordinador;
  private String inspeccionsi;
  private String inspeccionno;
  private String medidaproteccionsi;
  private String medidaproteccionno;
  private String entrenadoracargo;
  private String personadeapoyo;
  private String logoseguridad;
  private String codigoverificacion;
  private String evaluacionconocimientopregunta1;
  private String evaluacionconocimientopregunta2;
  private String evaluacionconocimientopregunta3;
  private String evaluacionconocimientopregunta4;
  private String evaluacionconocimientopregunta5;

  public Formatoinscripcion() {
  }

  public Formatoinscripcion(Long id) {
    this.id = id;
  }


}
