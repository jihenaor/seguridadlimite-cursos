package com.seguridadlimite.models.entity;

import java.io.Serializable;

public class Trabajadorgrupolist implements Serializable {

  private Long idtrabajador;
  private Long idaprendiz;
  private Long idgrupo;
  private String tipodocumento;
  private String numerodocumento;
  private String nombrecompleto;
  private String base64;
  private String empresa;

  public Long getIdtrabajador() {
    return idtrabajador;
  }

  public void setIdtrabajador(Long idtrabajador) {
    this.idtrabajador = idtrabajador;
  }

  public Long getIdaprendiz() {
    return idaprendiz;
  }

  public void setIdaprendiz(Long idaprendiz) {
    this.idaprendiz = idaprendiz;
  }

  public Long getIdgrupo() {
    return idgrupo;
  }

  public void setIdgrupo(Long idgrupo) {
    this.idgrupo = idgrupo;
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

  public String getNombrecompleto() {
    return nombrecompleto;
  }

  public void setNombrecompleto(String nombrecompleto) {
    this.nombrecompleto = nombrecompleto;
  }

  public String getBase64() {
    return base64;
  }

  public void setBase64(String base64) {
    this.base64 = base64;
  }

  public String getEmpresa() {
    return empresa;
  }

  public void setEmpresa(String empresa) {
    this.empresa = empresa;
  }
}
