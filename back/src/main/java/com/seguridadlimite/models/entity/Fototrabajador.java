package com.seguridadlimite.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Fototrabajador implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  
  private String base64;
  
  private String ext;

  public Fototrabajador(Long id, String ext) {
    this.id = id;
    this.ext = ext;
  }
}
