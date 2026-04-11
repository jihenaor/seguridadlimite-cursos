package com.seguridadlimite.models.aprendiz.domain;

import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.documentos.domain.Documento;
import com.seguridadlimite.models.enfasis.domain.Enfasis;
import com.seguridadlimite.models.nivel.domain.Nivel;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sl_aprendices")
@Data
public class AprendizRSinTrabajador implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  private Integer id;
  
  @Column(name = "fechaverificacion")
  @Temporal(TemporalType.TIMESTAMP)
  private Date
          fechaverificacion;
  
  @Size(max = 20)
  @Column(name = "codigoverificacion")
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
  private BigDecimal evaluacionentrenamiento;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String cumplehoras;
  
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
  
  private Integer idenfasis;
  
  @JoinColumn(name = "idenfasis", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne
  private Enfasis enfasis;
  
  private Integer idnivel;
  
  @JoinColumn(name = "idnivel", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne
  private Nivel nivel;

  @Size(max = 45)
  private String labordesarrolla;

  @Size(max = 1)
  private String siso;
  
  @Size(max = 1)
  private String operativa;

  @Size(max = 1)
  private String administrativa;

  @Size(max = 20)
  private String otralabor;

  @Basic(optional = false)
  @NotNull
  private String eps;

  @Basic(optional = false)
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
  @Column(name = "medicamentos")
  private String medicamentos;
  
  @Size(max = 100)
  @Column(name = "enfermedades")
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

  @Size(max = 1)
  private String documentoidentidad;

  @Size(max = 1)
  private String pagoseguridadsocial;

  @Size(max = 1)
  private String certificadolaboral;

  @Size(max = 1)
  private String certificadoarl;

  @Size(max = 1)
  private String certificadoformacion;

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
  
  private String exteteorica;
  
  private String extepractica;
  
  private String exteenfasis;

  @Basic(optional = false)
  @NotNull
  private Integer idtrabajador;
  
  @Basic(optional = false)
  @NotNull
  private String estadoinscripcion;
  
  @Basic(optional = false)
  @NotNull
  private String aceptaterminos;

  private String fechareentrenamiento;
  private String fechaemision;

  @Transient
  private List<Documento> documentos;
  
  @Transient
  private List<Asistencia> asistencias;

  @Basic(optional = false)
  @NotNull
  private String tienefirma;

  private String ciudadreentrenamiento;

  private String ciudadexpedicion;

  private String empresa;
  private String nit;
  private String representantelegal;

  public AprendizRSinTrabajador() {
  }
  
}
