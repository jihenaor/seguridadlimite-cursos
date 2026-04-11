package com.seguridadlimite.models.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Trabajadordocumento implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  
  private String ext;
  
  private String adjuntodocumento;
  
  private String base64a;
   
  private String base64b;


  public Trabajadordocumento() {
  }
	
}
