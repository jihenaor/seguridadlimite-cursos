package com.seguridadlimite.models.entity;

import lombok.Data;

import com.seguridadlimite.models.documentos.domain.Documento;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;


// @Data
// @Entity - tabla "documentosnivel" no existe en BD; clase sin uso activo
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







  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }
}
