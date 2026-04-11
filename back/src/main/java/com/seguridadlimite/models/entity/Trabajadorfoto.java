package com.seguridadlimite.models.entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Table(name = "trabajadoresfoto")
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdtrabajador() {
    return idtrabajador;
  }

  public void setIdtrabajador(Long idtrabajador) {
    this.idtrabajador = idtrabajador;
  }

  public byte[] getImagen() {
    return imagen;
  }

  public void setImagen(byte[] imagen) {
    this.imagen = imagen;
  }

  public String getBase64() {
    return base64;
  }

  public void setBase64(String base64) {
    this.base64 = base64;
  }
}
