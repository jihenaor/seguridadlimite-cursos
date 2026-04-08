package com.seguridadlimite.models.evaluacion.dominio;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sl_evaluaciones")
@Data
public class Evaluacion implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Long id;

  private Integer numerorespuesta;
  
  @Size(max = 320)
  private String textorespuesta;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String respuestacorrecta;
  
  @Basic(optional = false)
  @NotNull
  private Long idaprendiz;
  
  @Basic(optional = false)
  @NotNull
  private Long idpregunta;

  @JoinColumn(name = "idpregunta", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private Pregunta pregunta;

  @Basic(optional = false)
  @NotNull
  private Integer numero;
  
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

  @Transient
  private Aprendiz aprendiz;

  public Evaluacion() {
  }

  public Evaluacion(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getNumerorespuesta() {
    return numerorespuesta;
  }

  public void setNumerorespuesta(Integer numerorespuesta) {
    this.numerorespuesta = numerorespuesta;
  }

  public String getTextorespuesta() {
    return textorespuesta;
  }

  public void setTextorespuesta(String textorespuesta) {
    this.textorespuesta = textorespuesta;
  }

  public String getRespuestacorrecta() {
    return respuestacorrecta;
  }

  public void setRespuestacorrecta(String respuestacorrecta) {
    this.respuestacorrecta = respuestacorrecta;
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

  public Long getIdaprendiz() {
    return idaprendiz;
  }

  public void setIdaprendiz(Long idaprendiz) {
    this.idaprendiz = idaprendiz;
  }

  public Long getIdpregunta() {
	return idpregunta;
  }

  public void setIdpregunta(Long idpregunta) {
	this.idpregunta = idpregunta;
  }
  
  public Integer getNumero() {
	return numero;
  }

  public void setNumero(Integer numero) {
	this.numero = numero;
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
    if (!(object instanceof Evaluacion)) {
      return false;
    }
    Evaluacion other = (Evaluacion) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.pojos.Evaluacion[ id=" + id + " ]";
  }
}
