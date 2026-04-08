package com.seguridadlimite.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aprendices")

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Trabajadorauth getTrabajador() {
    return this.trabajador;
  }  

  public void setIdtrabajador(Trabajadorauth trabajador) {
    this.trabajador = trabajador;
  }

}
