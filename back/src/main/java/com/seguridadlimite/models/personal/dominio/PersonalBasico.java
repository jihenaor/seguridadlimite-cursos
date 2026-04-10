/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridadlimite.models.personal.dominio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * Subconjunto de columnas de {@code sl_personal} para relaciones JPA ligeras (p. ej. permisos).
 * La autenticación usa {@link Personal} (misma tabla), que incluye {@code password}, {@code role}, email, etc.
 */
@Entity
@Table(name = "sl_personal")
@Data
public class PersonalBasico implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Long id;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2)
  private String tipodocumento;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 15)
  private String numerodocumento;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 60)
  private String nombrecompleto;

  public PersonalBasico() {
  }
}
