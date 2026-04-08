package com.seguridadlimite.models.programa.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name="sl_programas")
@Data
public class Programa implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @NotNull
  private Long id;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 45)
  private String nombre;

  public Programa() {
  }

  public Programa(Long id) {
    this.id = id;
  }
}