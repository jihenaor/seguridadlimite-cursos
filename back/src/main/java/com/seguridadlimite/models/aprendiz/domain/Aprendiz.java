package com.seguridadlimite.models.aprendiz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.asistencia.domain.Modulo;
import com.seguridadlimite.models.audit.AuditAprendizListener;
import com.seguridadlimite.models.audit.AuditableEntity;
import com.seguridadlimite.models.documentos.domain.Documento;
import com.seguridadlimite.models.enfasis.domain.Enfasis;
import com.seguridadlimite.models.nivel.domain.Nivel;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturasBasico;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sl_aprendices")
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class, AuditAprendizListener.class})
@Data
public class Aprendiz extends AuditableEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Integer id;

  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaverificacion;

  @Size(max = 20)
  private String codigoverificacion;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String pagocurso;

@Basic(optional = false)
   @NotNull
   @Column(name = "evaluacionformacion", columnDefinition = "DECIMAL")
   private Double evaluacionformacion;
  
@Basic(optional = false)
   @NotNull
   @Column(name = "evaluacionentrenamiento", columnDefinition = "DECIMAL")
   private Double evaluacionentrenamiento;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String cumplehoras;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String abonocurso;
  
  @Basic(optional = false)
  @NotNull
  private Integer idenfasis;

  @JoinColumn(name = "idenfasis", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne
  private Enfasis enfasis;

  @Size(max = 40)
  private String otroenfasis;

  @Basic(optional = true)
  @Column(name = "id_permiso")
  private Integer idPermiso;

  @JoinColumn(name = "id_permiso", referencedColumnName = "id_permiso", insertable = false, updatable = false)
  @ManyToOne
  private PermisoTrabajoAlturasBasico permisoTrabajoAlturas;

  @Basic(optional = false)
  @NotNull
  private Integer idnivel;

  @JoinColumn(name = "idnivel", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(fetch = FetchType.EAGER)
  private Nivel nivel;
  
  @Size(max = 1)
  private String tieneexperienciaaltura;

  @Size(max = 45)
  private String labordesarrolla;

  @Size(max = 1)
  private String tipovinculacionlaboral;

  @Size(max = 1)
  private String regimenafiliacionseguridadsocial;

  @Size(max = 1)
  private String documentoidentidad;

  @Size(max = 1)
  private String ultimopagoseguridadsocial;

  @Size(max = 1)
  private String afiliacionseguridadsocial;
  
  @Size(max = 1)
  private String certificadoaptitudmedica;

  @Size(max = 1)
  private String certificadotrabajadorautorizado;

  @Size(max = 1)
  private String certificadonivelcoordinador;

  @Basic(optional = false)
  @NotNull
  private String eps;

  @Basic(optional = false)
  @Size(max=60)
  @NotNull
  private String arl;

  @Size(min = 1, max = 1)
  private String sabeleerescribir;

  @Size(max = 45)
  @Column(name = "niveleducativo")
  private String niveleducativo;

  @Size(max = 45)
  @Column(name = "cargoactual")
  private String cargoactual;
  
  @Size(max = 100)
  @Column(name = "alergias")
  private String alergias;
  
  @Size(max = 100)
  private String medicamentos;
  
  @Size(max = 100)
  private String enfermedades;
  
  @Size(max = 100)
  private String lesiones;

  @Size(max = 100)
  private String drogas;

  @Size(max = 60)
  private String nombrecontacto;

  @Size(max = 45)
  private String telefonocontacto;

  @Size(max = 45)
  private String parentescocontacto;

  @Size(max = 1)
  private String embarazo;

  @Size(max = 2)
  private String mesesgestacion;

  @Basic(optional = false)
  @NotNull
  private String estadoinscripcion;

  @Basic(optional = false)
  @NotNull
  private Integer idtrabajador;

  @OneToOne(optional = false)
  @JoinColumn(name = "idtrabajador", referencedColumnName = "id", insertable = false, updatable = false)
  private Trabajador trabajador;

  private String comentariosencuesta;
  
  @Transient
  @JsonFormat
  (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es-CO", timezone = "America/Bogota")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaasistencia;

@Basic(optional = false)
   @NotNull
   @Column(name = "eingreso", columnDefinition = "DECIMAL")
   private Double eingreso;

@Basic(optional = false)
   @NotNull
   @Column(name = "eenfasis", columnDefinition = "DECIMAL")
   private Double eenfasis;
  
@Basic(optional = false)
   @NotNull
   @Column(name = "eteorica1", columnDefinition = "DECIMAL")
   private Double eteorica1;
   
   @Basic(optional = false)
   @NotNull
   @Column(name = "eteorica2", columnDefinition = "DECIMAL")
   private Double eteorica2;
   
   @Basic(optional = false)
   @NotNull
   @Column(name = "epractica", columnDefinition = "DECIMAL")
   private Double epractica;
  
  /** Columna MySQL TINYINT (p. ej. 0–2 intentos de verificación). */
  @Basic(optional = false)
  @NotNull
  private Byte intentos;

  private String exteteorica;
  
  private String extepractica;
  
  private String exteenfasis;

  @Column(name = "duraciontotal", columnDefinition = "SMALLINT")
   private Integer duraciontotal;
  
  @Transient
  private Long idasistencia;
  
  @Transient
  private Integer horas;

  @Size(max = 1)
  @Column(name = "tienefirma")
  private String tienefirma;

  private String fechaemision;

  private String fechareentrenamiento;

  private String ciudadreentrenamiento;

  private String ciudadexpedicion;

  private String empresa;
  private String nit;
  private String representantelegal;

  private String fechaencuesta;

  private String fechainscripcion;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat
          (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "es-CO", timezone = "America/Bogota")
  private Date fechalimiteinscripcion;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat
          (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "es-CO", timezone = "America/Bogota")
  private Date fechalimiteevaluacion;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat
          (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "es-CO", timezone = "America/Bogota")
  private Date fechalimiteencuesta;

  @Transient
  private Boolean aprobocurso;

  @Transient
  private String base64;

  @Transient
  private int minutosEvaluacion;

  /** Coincide con columna MySQL TINYINT (signed −128…127). */
  private Byte contadorcertificados;

  @Transient
  private List<Documento> documentos;
  public Aprendiz(Integer id) {
    this.id = id;
  }

  @Transient
  private List<Asistencia> asistencias;

  @Transient
  private List<Modulo> modulos;

  @Transient
  private double totalhoras;

  @Size(max = 20)
  private String departamentodomicilio;

  @Size(max = 40)
  private String ciudaddomicilio;

  @Size(max = 60)
  private String direcciondomicilio;

  @Column(name = "fechaultimaasistencia")
  private String fechaUltimaAsistencia;

  @Transient
  private boolean asistenciaCompleta;
}
