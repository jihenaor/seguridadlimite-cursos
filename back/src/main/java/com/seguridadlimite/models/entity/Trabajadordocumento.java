package com.seguridadlimite.models.entity;

import java.io.Serializable;

public class Trabajadordocumento implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  
  private String ext;
  
  private String adjuntodocumento;
  
  private String base64a;
   
  private String base64b;


  public Trabajadordocumento() {
  }
	
  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getExt() {
	  return ext;
  }
	
  public void setExt(String exta) {
	this.ext = exta;
  }
	
  public String getAdjuntodocumento() {
	return adjuntodocumento;
  }

  public void setAdjuntodocumento(String adjuntodocumento) {
	this.adjuntodocumento = adjuntodocumento;
  }

  public String getBase64a() {
	return base64a;
  }
	
  public void setBase64a(String base64a) {
	this.base64a = base64a;
  }
	
  public String getBase64b() {
	  return base64b;
  }
	
  public void setBase64b(String base64b) {
	this.base64b = base64b;
  }
}
