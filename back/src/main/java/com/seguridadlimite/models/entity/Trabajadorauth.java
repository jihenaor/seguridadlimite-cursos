package com.seguridadlimite.models.entity;

import lombok.Data;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

// @Entity - clase sin uso activo; usar models/trabajador/dominio/Trabajador.java
@Table(name = "sl_trabajadores")
@Data
public class Trabajadorauth implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2)
  @Column(name = "tipodocumento")
  private String tipodocumento;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 16)
  @Column(name = "numerodocumento")
  private String numerodocumento;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "primernombre")
  private String primernombre;

  @Size(max = 20)
  @Column(name = "segundonombre")
  private String segundonombre;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "primerapellido")
  private String primerapellido;
  @Size(max = 20)
  @Column(name = "segundoapellido")
  private String segundoapellido;

  @Transient
  private String nombrecompleto;

  @Transient
  private String base64;

  public Trabajadorauth() {
  }

public String getNombrecompleto() {
    nombrecompleto = primernombre + " " +
        (segundonombre == null ? "" : segundonombre) + " " +
        primerapellido + " " +
        (segundoapellido == null ? "" : segundoapellido);

    return nombrecompleto;
  }

}
