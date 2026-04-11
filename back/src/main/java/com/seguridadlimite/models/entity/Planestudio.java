
package com.seguridadlimite.models.entity;

import lombok.Data;

import com.seguridadlimite.models.nivel.domain.Nivel;
import jakarta.persistence.*;

import java.io.Serializable;


// @Entity - tabla "planestudio" no existe en BD; clase sin uso activo
@Table(name = "planestudio")
@Data
public class Planestudio implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;
  @Basic(optional = false)
  @Column(name = "modulo")
  private String modulo;
  @Column(name = "tema")
  private String tema;
  @Column(name = "descripcion")
  private String descripcion;
  @Column(name = "hidrocarburos")
  private String hidrocarburos;
  @Column(name = "construccion")
  private String construccion;
  @Column(name = "electrico")
  private String electrico;
  @Column(name = "contexto")
  private String contexto;
  @Column(name = "instrumentos")
  private String instrumentos;
  @Column(name = "tecnicaevaluacion")
  private String tecnicaevaluacion;
  @Column(name = "nivellectoescritura")
  private String nivellectoescritura;
  @Column(name = "dia1")
  private String dia1;
  @Column(name = "dia2")
  private String dia2;
  @Column(name = "dia3")
  private String dia3;
  @Column(name = "dia4")
  private String dia4;
  @Column(name = "dia5")
  private String dia5;
  @Column(name = "dia6")
  private String dia6;
  @Column(name = "dia7")
  private String dia7;
  @Column(name = "dia8")
  private String dia8;
  @Column(name = "dia9")
  private String dia9;
  @Column(name = "dia10")
  private String dia10;

  @JoinColumn(name = "idnivel", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Nivel idnivel;

  public Planestudio() {
  }

  public Planestudio(Long id) {
    this.id = id;
  }

  public Planestudio(Long id, String modulo) {
    this.id = id;
    this.modulo = modulo;
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
    if (!(object instanceof Planestudio)) {
      return false;
    }
    Planestudio other = (Planestudio) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "training.Planestudio[ id=" + id + " ]";
  }
  
}
