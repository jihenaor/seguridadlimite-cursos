package com.seguridadlimite.models.grupopregunta.domain;

import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.programa.model.Programa;
import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sl_grupospregunta")
@Data
public class Grupopregunta implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  private Long id;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 60)
  private String nombre;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  private String tipoevaluacion;

  @Basic(optional = false)
  @NotNull
  private int orden;

  @JoinColumn(name = "idprograma", referencedColumnName = "id")
  @ManyToOne
  private Programa programa;

  @Transient
  private List<Evaluacion> evaluacions;

  public Grupopregunta() {
  }

  public Grupopregunta(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "com.pojos.Grupospregunta[ id=" + id + " ]";
  }

}

/*



@Entity
@Table(name = "sl_grupospregunta")

public class Grupoevaluacion implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "id")
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 60)
  @Column(name = "nombre")
  private String nombre;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  @Column(name = "tipoevaluacion")
  private String tipoevaluacion;

  @Basic(optional = false)
  @NotNull
  @Column(name = "orden")
  private int orden;

  @Basic(optional = false)
  @NotNull
  private Long idprograma;


  public Grupoevaluacion() {
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

  public String getTipoevaluacion() {
    return tipoevaluacion;
  }

  public void setTipoevaluacion(String tipoevaluacion) {
    this.tipoevaluacion = tipoevaluacion;
  }

  public int getOrden() {
    return orden;
  }

  public void setOrden(int orden) {
    this.orden = orden;
  }

  public Long getIdprograma() {
    return idprograma;
  }

  public void setIdprograma(Long idprograma) {
    this.idprograma = idprograma;
  }

  public List<Evaluacion> getEvaluacions() {
	return evaluacions;
  }

  public void setEvaluacions(List<Evaluacion> evaluacions) {
	this.evaluacions = evaluacions;
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
    if (!(object instanceof Grupoevaluacion)) {
      return false;
    }
    Grupoevaluacion other = (Grupoevaluacion) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.pojos.Grupospregunta[ id=" + id + " ]";
  }
}

 */