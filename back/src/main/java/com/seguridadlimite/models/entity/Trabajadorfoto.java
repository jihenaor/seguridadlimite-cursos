package com.seguridadlimite.models.entity;

import lombok.Data;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "trabajadoresfoto")
@Data
public class Trabajadorfoto implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

  @Basic(optional = false)
  @Column(name = "idtrabajador")
  private Long idtrabajador;
  
  @Basic(optional = false)
  @Lob
  @Column(name = "imagen")
  private byte[] imagen;
  
  @Transient
  private String base64;

  public Trabajadorfoto() {
  }

}
