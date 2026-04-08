package com.seguridadlimite.models.pregunta.domain;

import com.seguridadlimite.models.enfasis.domain.Enfasis;
import com.seguridadlimite.models.nivel.domain.Nivel;
import com.seguridadlimite.models.entity.Respuesta;
import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;
import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sl_preguntas")
@Data
public class Pregunta implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Long id;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 500)
  private String pregunta;

  private Integer numerorespuestacorrecta;

  private Integer orden;

  private Long idgrupo;

  @JoinColumn(name = "idgrupo", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private Grupopregunta grupo;

  private Long idenfasis;

  @JoinColumn(name = "idenfasis", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne
  private Enfasis enfasis;

  private Long idnivel;

  @JoinColumn(name = "idnivel", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne
  private Nivel nivel;

  @Basic(optional = false)
  @NotNull
  private Integer type;

  @Basic(optional = false)
  @NotNull
  private String estado;

  private String agrupador1;

  private String agrupador2;

  private String agrupador3;

  private String opcionesrespuesta;

  private String nombreopcion;

  private String diflectura;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpregunta")
  private List<Respuesta> respuestas;

  @Transient
  private Boolean required;

  @Transient
  private String respuestacorrecta;

  @Transient
  private Long idevaluacion;

  @Transient
  private Integer numerorespuesta;

  @Transient
  private List<TipoevaluacionEstadistica> tipoevaluacionEstadisticas;

  @Transient
  private String textorespuesta;

  @Transient
  private String base64;
}
