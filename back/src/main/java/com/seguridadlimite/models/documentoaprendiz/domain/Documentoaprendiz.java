package com.seguridadlimite.models.documentoaprendiz.domain;

import com.seguridadlimite.models.documentos.domain.Documento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Entity
@Table(name = "sl_documentoaprendiz")
@Data
public class Documentoaprendiz implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  private Long iddocumento;

  @JoinColumn(name = "iddocumento", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne
  private Documento documento;

  @Basic(optional = false)
  private int idaprendiz;
  
  private String documentokey;
  
  private String ext;
  
  @Transient
  private String base64;
  
  public Documentoaprendiz() {
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
    if (!(object instanceof Documentoaprendiz)) {
      return false;
    }
    Documentoaprendiz other = (Documentoaprendiz) object;
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
