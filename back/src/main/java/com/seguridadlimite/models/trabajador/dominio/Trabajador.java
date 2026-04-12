package com.seguridadlimite.models.trabajador.dominio;

import lombok.extern.slf4j.Slf4j;

import com.seguridadlimite.util.UppercaseTransform;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;

@Getter
@Setter
@UppercaseTransform
@Slf4j
public class Trabajador implements Serializable {

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
  @Size(min = 1, max = 16)
  private String numerodocumento;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 60)
  private String primernombre;

  @Size(max = 20)
  private String segundonombre;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 0, max = 20)
  private String primerapellido;
  
  @Size(max = 20)
  private String segundoapellido;
  
  @Transient
  private String nombrecompleto;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 0, max = 1)
  @Column(name = "genero")
  private String genero;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 0, max = 2)
  private String nacionalidad;

  @Size(max = 10)
  private String fechanacimiento;

  // @Basic(optional = false)
  // @NotNull

//  @NotNull
  @Size(max = 3)
  private String tiposangre;

  @Size(max = 50)
  private String ocupacion;

  @Size(max = 10)
  private String celular;

//  @Size(max = 40, unique = true)
  @Size(max = 80)
  private String correoelectronico;
  
  @Basic(optional = false)
  @NotNull
  @Size(max = 1)
  private String adjuntodocumento;
  
  @Size(max = 4)
  private String ext;

  @Basic(optional = false)
  @NotNull
  @Column(name = "create_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createAt;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "update_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateAt;

  @Basic(optional = false)
  @NotNull
  @Size(max = 1)
  private String foto;

  @Basic(optional = false)
  @NotNull
  @Size(max = 1)
  private String valido;

  
  @Transient
  private String base64;

  @Transient
  private String base64a;
  
  @Transient
  private String base64b;

  @Transient
  private String exception;
  
  @Transient
  private Integer idaprendiz;

  @Transient
  private Long idenfasis;

  @Transient
  private Long idnivel;
  
  @Transient
  private String inscripcionconscaner;


  public Trabajador() {
  }

  public Trabajador(Long id) {
    this.id = id;
  }
  
  public Trabajador(Exception e) {
	  try {
		  StringWriter sw = new StringWriter();
		  PrintWriter pw = new PrintWriter(sw);
		  e.printStackTrace(pw);
		  this.exception = sw.toString();
	  } catch (Exception ex) {
		  log.error("Se capturó una excepción: ", ex);
	  }
  }




  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Trabajador)) {
      return false;
    }
    Trabajador other = (Trabajador) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.pojos.Trabajadores[ id=" + id + " ]";
  }

}