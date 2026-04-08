package com.seguridadlimite.models.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Trabajadorformatoinscripcion implements Serializable {

  private static final long serialVersionUID = 1L;
  private Long idtrabajador;
  private String tipodocumento;
  private String numerodocumento;
  private String primernombre;
  private String segundonombre;
  private String primerapellido;
  private String segundoapellido;
  private String nombrecompleto;
  private String niveleducativo;
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
  private String tieneexperienciaaltura;
  private String labordesarrolla;
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
  private String adjuntodocumento;
  private String ext;
  private String foto;
  private String valido;
  private String base64;
  private Long idaprendiz;
  private String pagocurso;
  private Long idempresa;
  private Long idenfasis;
  private Long idnivel;
  private Long idgrupo;
  private String aceptaterminos;
  private String estadoinscripcion;
}
