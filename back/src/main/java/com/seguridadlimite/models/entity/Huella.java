package com.seguridadlimite.models.entity;

import lombok.Data;

import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Data
@Entity
@Table(name = "sl_huellas")
public class Huella implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

  @Basic(optional = false)
  private Long idtrabajador;
  
  @JoinColumn(name = "idtrabajador", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne
  private Trabajador trabajador;
  
  @Basic(optional = false)
  @Size(max = 1000)
  private String huella;
  

  public Huella() {
  }

  public Huella(Long id) {
    this.id = id;
  }







}