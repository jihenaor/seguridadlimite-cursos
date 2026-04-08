package com.seguridadlimite.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class FirmaAprendiz implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  
  private String base64;
  

}
