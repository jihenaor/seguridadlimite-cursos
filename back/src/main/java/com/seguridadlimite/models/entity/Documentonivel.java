package com.seguridadlimite.models.entity;

import com.seguridadlimite.models.documentos.domain.Documento;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "documentosnivel")

public class Documentonivel implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  private Long idnivel;
  
  @JoinColumn(name = "iddocumento", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Documento documento;
  
  public Documentonivel() {
  }

  public Documentonivel(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdnivel() {
    return idnivel;
  }

  public void setIdnivel(Long idnivel) {
    this.idnivel = idnivel;
  }

  public Documento getDocumento() {
    return documento;
  }

  public void setDocumento(Documento documento) {
    this.documento = documento;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }
}
