package com.seguridadlimite.models.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sl_solicitudes")

public class Solicitud implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

  @Size(max = 20)
  @Column(name = "numerodocumento")
  private String numerodocumento;
  
  @Size(max = 100)
  @Column(name = "nombrecontacto")
  private String nombrecontacto;

  @Size(max = 100)
  @Column(name = "email")
  private String email;
  
  @Size(max = 20)
  @Column(name = "celular")
  private String celular;
  
  @Size(max = 100)
  @Column(name = "horario")
  private String horario;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "cupo")
  private int cupo;
  
  @Size(max = 30)
  @Column(name = "programa")
  private String programa;
  
  @Size(max = 30)
  @Column(name = "nivel")
  private String nivel;
  
  @Size(max = 100)
  @Column(name = "empresa")
  private String empresa;
  
  @Size(max = 1)
  @Column(name = "aucodestad")
  private String aucodestad;
  
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
  
  public Solicitud() {
  }

  public Solicitud(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getNumerodocumento() {
	return numerodocumento;
}

public void setNumerodocumento(String numerodocumento) {
	this.numerodocumento = numerodocumento;
}

public String getNombrecontacto() {
	return nombrecontacto;
}

public void setNombrecontacto(String nombrecontacto) {
	this.nombrecontacto = nombrecontacto;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getCelular() {
	return celular;
}

public void setCelular(String celular) {
	this.celular = celular;
}

public String getHorario() {
	return horario;
}

public void setHorario(String horario) {
	this.horario = horario;
}

public int getCupo() {
	return cupo;
}

public void setCupo(int cupo) {
	this.cupo = cupo;
}

public String getPrograma() {
	return programa;
}

public void setPrograma(String programa) {
	this.programa = programa;
}

public String getNivel() {
	return nivel;
}

public void setNivel(String nivel) {
	this.nivel = nivel;
}

public String getEmpresa() {
	return empresa;
}

public void setEmpresa(String empresa) {
	this.empresa = empresa;
}

public String getAucodestad() {
	return aucodestad;
}

public void setAucodestad(String aucodestad) {
	this.aucodestad = aucodestad;
}

public Date getCreateAt() {
	return createAt;
}

public void setCreateAt(Date createAt) {
	this.createAt = createAt;
}

public Date getUpdateAt() {
	return updateAt;
}

public void setUpdateAt(Date updateAt) {
	this.updateAt = updateAt;
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
    if (!(object instanceof Solicitud)) {
      return false;
    }
    Solicitud other = (Solicitud) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.pojos.Grupos[ id=" + id + " ]";
  }  
}
