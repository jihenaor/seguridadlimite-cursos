
package com.seguridadlimite.models.entity;

import com.seguridadlimite.models.nivel.domain.Nivel;
import jakarta.persistence.*;

import java.io.Serializable;


// @Entity — tabla "planestudio" no existe en BD; clase sin uso activo
@Table(name = "planestudio")
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getModulo() {
    return modulo;
  }

  public void setModulo(String modulo) {
    this.modulo = modulo;
  }

  public String getTema() {
    return tema;
  }

  public void setTema(String tema) {
    this.tema = tema;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getHidrocarburos() {
    return hidrocarburos;
  }

  public void setHidrocarburos(String hidrocarburos) {
    this.hidrocarburos = hidrocarburos;
  }

  public String getConstruccion() {
    return construccion;
  }

  public void setConstruccion(String construccion) {
    this.construccion = construccion;
  }

  public String getElectrico() {
    return electrico;
  }

  public void setElectrico(String electrico) {
    this.electrico = electrico;
  }

  public String getContexto() {
    return contexto;
  }

  public void setContexto(String contexto) {
    this.contexto = contexto;
  }

  public String getInstrumentos() {
    return instrumentos;
  }

  public void setInstrumentos(String instrumentos) {
    this.instrumentos = instrumentos;
  }

  public String getTecnicaevaluacion() {
    return tecnicaevaluacion;
  }

  public void setTecnicaevaluacion(String tecnicaevaluacion) {
    this.tecnicaevaluacion = tecnicaevaluacion;
  }

  public String getNivellectoescritura() {
    return nivellectoescritura;
  }

  public void setNivellectoescritura(String nivellectoescritura) {
    this.nivellectoescritura = nivellectoescritura;
  }

  public String getDia1() {
    return dia1;
  }

  public void setDia1(String dia1) {
    this.dia1 = dia1;
  }

  public String getDia2() {
    return dia2;
  }

  public void setDia2(String dia2) {
    this.dia2 = dia2;
  }

  public String getDia3() {
    return dia3;
  }

  public void setDia3(String dia3) {
    this.dia3 = dia3;
  }

  public String getDia4() {
    return dia4;
  }

  public void setDia4(String dia4) {
    this.dia4 = dia4;
  }

  public String getDia5() {
    return dia5;
  }

  public void setDia5(String dia5) {
    this.dia5 = dia5;
  }

  public String getDia6() {
    return dia6;
  }

  public void setDia6(String dia6) {
    this.dia6 = dia6;
  }

  public String getDia7() {
    return dia7;
  }

  public void setDia7(String dia7) {
    this.dia7 = dia7;
  }

  public String getDia8() {
    return dia8;
  }

  public void setDia8(String dia8) {
    this.dia8 = dia8;
  }

  public String getDia9() {
    return dia9;
  }

  public void setDia9(String dia9) {
    this.dia9 = dia9;
  }

  public String getDia10() {
    return dia10;
  }

  public void setDia10(String dia10) {
    this.dia10 = dia10;
  }

  public Nivel getIdnivel() {
    return idnivel;
  }

  public void setIdnivel(Nivel idnivel) {
    this.idnivel = idnivel;
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
