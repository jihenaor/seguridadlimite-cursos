
package com.seguridadlimite.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "sl_arls")
@Data
public class Arl implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Long id;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String nombre;

  public Arl() {
  }

  
}
