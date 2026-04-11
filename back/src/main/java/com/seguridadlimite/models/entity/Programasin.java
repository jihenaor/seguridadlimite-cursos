package com.seguridadlimite.models.entity;

import lombok.Data;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Data
@Entity
@Table(name="sl_programas")
public class Programasin implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @NotNull
  private Long id;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 45)
  private String nombre;
  
  public Programasin() {
  }

  public Programasin(Long id) {
    this.id = id;
  }




}