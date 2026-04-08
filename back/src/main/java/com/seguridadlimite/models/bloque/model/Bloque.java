package com.seguridadlimite.models.bloque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "sl_bloques")
@Data
public class Bloque implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Integer id;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String nombre;
}
