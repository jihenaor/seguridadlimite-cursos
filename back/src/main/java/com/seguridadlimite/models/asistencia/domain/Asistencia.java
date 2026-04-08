package com.seguridadlimite.models.asistencia.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "sl_asistencias")
@Data
@AllArgsConstructor
@Builder
public class Asistencia implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

  @Basic(optional = false)
  @NotNull
  private Long idaprendiz;

  private String fecha;

  @Basic(optional = false)
  @NotNull
  private Integer modulo;

  @Basic(optional = false)
  @NotNull
  private String contexto;

  @Basic(optional = false)
  @NotNull
  private String unidad;

  @Basic(optional = false)
  @NotNull
  private Integer dia;

  @Basic(optional = false)
  @NotNull
  private Double horas;

  @Size(max = 60)
  private String observacion;

  @Transient
  private Boolean selected;

  public Asistencia() {
    // Default constructor for JPA
  }
}
