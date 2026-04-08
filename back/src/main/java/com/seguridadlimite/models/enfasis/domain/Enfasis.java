package com.seguridadlimite.models.enfasis.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sl_enfasis")
@Data
public class Enfasis implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 45)
  private String nombre;

  private String estado;

  public Enfasis() {
  }

  public Enfasis(Long id) {
    this.id = id;
  }
}
