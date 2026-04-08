package com.seguridadlimite.models.entity;

public class Formatoevaluacion {

  private Long id;
  private String tipodocumento;
  private String numerodocumento;
  private String nombrecompleto;
  private String primernombre;
  private String segundonombre;
  private String primerapellido;
  private String segundoapellido;
  private String eteorica;
  private String epractica;
  private String eenfasis;
  private String etotal;

  public Formatoevaluacion() {
  }

  public Formatoevaluacion(Long id) {
    this.id = id;
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

  public String getNombrecompleto() {
	return nombrecompleto;
  }

  public void setNombrecompleto(String nombrecompleto) {
	this.nombrecompleto = nombrecompleto;
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

  public String getEteorica() {
	return eteorica;
  }

  public void setEteorica(String eteorica) {
	this.eteorica = eteorica;
  }

  public String getEpractica() {
	return epractica;
  }

  public void setEpractica(String epractica) {
	this.epractica = epractica;
  }

  public String getEenfasis() {
	return eenfasis;
  }

  public void setEenfasis(String eenfasis) {
	this.eenfasis = eenfasis;
  }

  public String getEtotal() {
	return etotal;
  }

  public void setEtotal(String etotal) {
	this.etotal = etotal;
  }
}
