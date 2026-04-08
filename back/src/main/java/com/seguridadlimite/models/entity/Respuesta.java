package com.seguridadlimite.models.entity;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "sl_respuestas")
@Data
public class Respuesta implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  private Integer numero;
  
  @Size(max = 200)
  private String respuesta;

  @Basic(optional = false)
  @Size(max = 1)
  private String tieneimagen;

  @Size(max = 20)
  private String nombreimagen;

  private Long idpregunta;

  @Transient
  private String base64;

  public Respuesta() {
  }

  public Respuesta(Long id) {
    this.id = id;
  }

  public Respuesta(Long id, int numero) {
    this.id = id;
    this.numero = numero;
  }
  public Respuesta(Integer numero, String respuesta, Long idpregunta) {
    this.numero = numero;
    this.respuesta = respuesta;
    this.idpregunta = idpregunta;
  }

}
