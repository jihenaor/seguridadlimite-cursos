package com.seguridadlimite.models.documentos.domain;

import lombok.Data;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;


@Data
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
  private int idaprendiz;
  
  @Transient
  private Long iddocumentoaprendiz;
  
  @Transient
  private String savedmsg;

  public Documento() {
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