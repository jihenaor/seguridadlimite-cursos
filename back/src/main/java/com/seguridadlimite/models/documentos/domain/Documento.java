package com.seguridadlimite.models.documentos.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "sl_documentos")

public class Documento implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "nombre")
  private String nombre;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "tipo")
  private String tipo;
  
  @Transient
  private String base64;
  
  @Transient
  private String ext;
  
  @Transient
  private Long idaprendiz;
  
  @Transient
  private Long iddocumentoaprendiz;
  
  @Transient
  private String savedmsg;

  public Documento() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTipo() {
	return tipo;
  }

  public void setTipo(String tipo) {
	this.tipo = tipo;
  }

  
  public String getBase64() {
	return base64;
  }

  public void setBase64(String base64) {
	this.base64 = base64;
  }

  public String getExt() {
	return ext;
  }

  public void setExt(String ext) {
	this.ext = ext;
  }
  

  public Long getIdaprendiz() {
	return idaprendiz;
  }

  public void setIdaprendiz(Long idaprendiz) {
	this.idaprendiz = idaprendiz;
  }
  
  public Long getIddocumentoaprendiz() {
	return iddocumentoaprendiz;
  }

  public void setIddocumentoaprendiz(Long iddocumentoaprendiz) {
	this.iddocumentoaprendiz = iddocumentoaprendiz;
  }

  
  public String getSavedmsg() {
	return savedmsg;
  }

  public void setSavedmsg(String savedmsg) {
	this.savedmsg = savedmsg;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Documento)) {
      return false;
    }
    Documento other = (Documento) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.pojos.Enfasis[ id=" + id + " ]";
  } 
}
