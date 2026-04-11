package com.seguridadlimite.models.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Trabajadorgrupolist implements Serializable {

  private Long idtrabajador;
  private Long idaprendiz;
  private Long idgrupo;
  private String tipodocumento;
  private String numerodocumento;
  private String nombrecompleto;
  private String base64;
  private String empresa;

}
