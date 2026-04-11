package com.seguridadlimite.models.entity;

import lombok.Data;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

// @Data
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












}