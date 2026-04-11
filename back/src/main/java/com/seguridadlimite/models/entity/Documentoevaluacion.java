package com.seguridadlimite.models.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Documentoevaluacion implements Serializable {

  private static final long serialVersionUID = 1L;

  private String tipo;
  
  private String ext;
  
  private String nombrearchivo;
  
  private String base64;
  
  private Long idaprendiz;

}
