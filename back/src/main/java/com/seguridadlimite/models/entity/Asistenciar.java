package com.seguridadlimite.models.entity;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

// @Entity — clase sin uso activo; usar models/asistencia/domain/Asistencia.java
@Table(name = "sl_asistencias")
public class Asistenciar implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "horas")
  private int horas;
  
  @Size(max = 60)
  @Column(name = "observacion")
  private String observacion;
  
  @Basic(optional = false)
  @NotNull
  private String fecha;
  
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
  
  @JoinColumn(name = "idaprendiz", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Aprendiz aprendiz;
  
  public Asistenciar() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getHoras() {
    return horas;
  }

  public void setHoras(int horas) {
    this.horas = horas;
  }

  public String getFecha() {
	return fecha;
  }

  public void setFecha(String fecha) {
	this.fecha = fecha;
}

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public Aprendiz getAprendiz() {
    return aprendiz;
  }

  public void setIAprendiz(Aprendiz aprendiz) {
    this.aprendiz = aprendiz;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }
}
