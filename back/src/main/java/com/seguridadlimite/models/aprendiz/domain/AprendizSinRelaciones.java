package com.seguridadlimite.models.aprendiz.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "sl_aprendices")
@Data
public class AprendizSinRelaciones implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  @Column(name = "pagocurso")
  private String pagocurso;
  
  @Basic(optional = false)
  @NotNull
  private Double evaluacionformacion;
  
  @Basic(optional = false)
  @NotNull
  private BigDecimal evaluacionentrenamiento;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  @Column(name = "cumplehoras")
  private String cumplehoras;
  
  private Long idgrupo;
  
  @Basic(optional = false)
  @NotNull
  private String estadoinscripcion;
  
  @Basic(optional = false)
  @NotNull
  private String aceptaterminos;

  @Basic(optional = false)
  @NotNull
  private Long idtrabajador;

  public AprendizSinRelaciones() {
  }

}
