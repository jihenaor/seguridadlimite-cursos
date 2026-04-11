package com.seguridadlimite.models.entity;

import lombok.Data;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "sl_solicitudes")

public class Solicitud implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

  @Size(max = 20)
  @Column(name = "numerodocumento")
  private String numerodocumento;
  
  @Size(max = 100)
  @Column(name = "nombrecontacto")
  private String nombrecontacto;

  @Size(max = 100)
  @Column(name = "email")
  private String email;
  
  @Size(max = 20)
  @Column(name = "celular")
  private String celular;
  
  @Size(max = 100)
  @Column(name = "horario")
  private String horario;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "cupo")
  private int cupo;
  
  @Size(max = 30)
  @Column(name = "programa")
  private String programa;
  
  @Size(max = 30)
  @Column(name = "nivel")
  private String nivel;
  
  @Size(max = 100)
  @Column(name = "empresa")
  private String empresa;
  
  @Size(max = 1)
  @Column(name = "aucodestad")
  private String aucodestad;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "create_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createAt;

  @Basic(optional = false)
  @NotNull
  @Column(name = "update_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateAt;
  
  public Solicitud() {
  }

  public Solicitud(Long id) {
    this.id = id;
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
    if (!(object instanceof Solicitud)) {
      return false;
    }
    Solicitud other = (Solicitud) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.pojos.Grupos[ id=" + id + " ]";
  }  
}