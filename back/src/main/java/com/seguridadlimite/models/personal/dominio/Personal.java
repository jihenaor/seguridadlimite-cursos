/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridadlimite.models.personal.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * Personal operativo sobre {@code sl_personal}. Incluye credenciales y rol; usada en login
 * ({@link com.seguridadlimite.models.personal.application.PersonalService#findByLogin},
 * {@code AuthController}, {@code AuthenticationService}, {@code JwtAuthenticationFilter}).
 * {@link PersonalBasico} mapea la misma tabla solo con datos básicos, no para autenticación.
 */
@Entity
@Table(name = "sl_personal")
@Data
public class Personal implements Serializable {

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
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String entrenador;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String supervisor;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String personaapoyo;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String aucodestad;

  @JsonIgnore
  @Basic(optional = false)
  @NotNull
  private String email;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  private String role;

  @JsonIgnore
  @Basic(optional = false)
  @NotNull
  private String password;

  private String personaautoriza;

  private String responsableemergencias;

  private String coordinadoralturas;

  @Transient
  private String name;
    
  @Transient
  private String date;

  
  @Transient
  private String mobile;
  
  @Transient
  private String department;

  public Personal() {
  }
}
