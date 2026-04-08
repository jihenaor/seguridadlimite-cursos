package com.seguridadlimite.models.entity;

import com.seguridadlimite.models.personal.dominio.Personal;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "sl_grupos")

public class Grupoconcursoid implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "cupoinicial")
  private int cupoinicial;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "cupofinal")
  private int cupofinal;
  
  @Size(max = 20)
  @Column(name = "codigoministerio")
  private String codigoministerio;
  
  @JoinColumn(name = "identrenador", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Personal entrenador;
  
  @JoinColumn(name = "idsupervisor", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Personal supervisor;
  
  @Basic(optional = false)
  @NotNull
  private Integer idnivel;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "fechafinal")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechafinal;

  public Grupoconcursoid() {
  }

  public Grupoconcursoid(Long id) {
    this.id = id;
  }

  public Grupoconcursoid(Long id, int cupoinicial, int cupofinal) {
    this.id = id;
    this.cupoinicial = cupoinicial;
    this.cupofinal = cupofinal;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getCupoinicial() {
    return cupoinicial;
  }

  public void setCupoinicial(int cupoinicial) {
    this.cupoinicial = cupoinicial;
  }

  public int getCupofinal() {
    return cupofinal;
  }

  public void setCupofinal(int cupofinal) {
    this.cupofinal = cupofinal;
  }

  public String getCodigoministerio() {
    return codigoministerio;
  }

  public void setCodigoministerio(String codigoministerio) {
    this.codigoministerio = codigoministerio;
  }

  public Personal getEntrenador() {
    return entrenador;
  }

  public void setEntrenador(Personal entrenador) {
    this.entrenador = entrenador;
  }

  public Personal getSupervisor() {
    return supervisor;
  }

  public void setSupervisor(Personal supervisor) {
    this.supervisor = supervisor;
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
    if (!(object instanceof Grupoconcursoid)) {
      return false;
    }
    Grupoconcursoid other = (Grupoconcursoid) object;
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
