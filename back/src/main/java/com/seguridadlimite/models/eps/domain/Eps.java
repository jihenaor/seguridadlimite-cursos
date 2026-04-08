
package com.seguridadlimite.models.eps.domain;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "sl_epss")
@Data
public class Eps implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String nombre;

  public Eps() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
}
