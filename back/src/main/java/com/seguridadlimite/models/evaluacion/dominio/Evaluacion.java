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
  private int idaprendiz;
  
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