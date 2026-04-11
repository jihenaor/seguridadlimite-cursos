package com.seguridadlimite.models.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

// @Entity - clase sin uso activo; usar models/trabajador/dominio/Trabajador.java
@Table(name = "sl_trabajadores")
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTipodocumento() {
    return tipodocumento;
  }

  public void setTipodocumento(String tipodocumento) {
    this.tipodocumento = tipodocumento;
  }

  public String getNumerodocumento() {
    return numerodocumento;
  }

  public void setNumerodocumento(String numerodocumento) {
    this.numerodocumento = numerodocumento;
  }

  public String getPrimernombre() {
    return primernombre;
  }

  public void setPrimernombre(String primernombre) {
    this.primernombre = primernombre;
  }

  public String getSegundonombre() {
    return segundonombre;
  }

  public void setSegundonombre(String segundonombre) {
    this.segundonombre = segundonombre;
  }

  public String getPrimerapellido() {
    return primerapellido;
  }

  public void setPrimerapellido(String primerapellido) {
    this.primerapellido = primerapellido;
  }

  public String getSegundoapellido() {
    return segundoapellido;
  }

  public void setSegundoapellido(String segundoapellido) {
    this.segundoapellido = segundoapellido;
  }

  public String getNombrecompleto() {
    nombrecompleto = primernombre + " " +
        (segundonombre == null ? "" : segundonombre) + " " +
        primerapellido + " " +
        (segundoapellido == null ? "" : segundoapellido);

    return nombrecompleto;
  }

  public String getBase64() {
    return base64;
  }

  public void setBase64(String base64) {
    this.base64 = base64;
  }
}
