package com.seguridadlimite.models.entity;

import java.io.Serializable;

public class Documentoevaluacion implements Serializable {

  private static final long serialVersionUID = 1L;

  private String tipo;
  
  private String ext;
  
  private String nombrearchivo;
  
  private String base64;
  
  private Long idaprendiz;

  public String getTipo() {
	return tipo;
  }

  public void setTipo(String tipo) {
	this.tipo = tipo;
  }

  public String getExt() {
	return ext;
  }

  public void setExt(String ext) {
	this.ext = ext;
  }

  public String getNombrearchivo() {
	return nombrearchivo;
  }

public void setNombrearchivo(String nombrearchivo) {
	this.nombrearchivo = nombrearchivo;
}

public String getBase64() {
	return base64;
}

public void setBase64(String base64) {
	this.base64 = base64;
}

public Long getIdaprendiz() {
	return idaprendiz;
}

public void setIdaprendiz(Long idaprendiz) {
	this.idaprendiz = idaprendiz;
}


}
