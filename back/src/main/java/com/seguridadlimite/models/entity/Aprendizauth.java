package com.seguridadlimite.models.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

// @Data
// @Entity - tabla "aprendices" no existe; clase sin uso activo
@Table(name = "sl_aprendices")
public class
Aprendizauth implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

  @Basic(optional = false)
  private Trabajadorauth trabajador;

  public Aprendizauth() {
  }

  public Aprendizauth(Long id) {
    this.id = id;
  }





}
