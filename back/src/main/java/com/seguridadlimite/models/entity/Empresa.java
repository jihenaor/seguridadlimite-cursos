package com.seguridadlimite.models.entity;

import com.seguridadlimite.models.enfasis.domain.Enfasis;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "sl_empresas")
@Data
public class Empresa implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
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
  @Size(min = 1, max = 100)
  private String nombre;
  
  @Size(max = 100)
  private String direccion;
//  @Size(max = 100)
//  private String direccion;
  
  @Size(max = 45)
  @Column(name = "telefono")
  private String telefono;
  @Size(max = 60)
  @Column(name = "nombrecontacto")
  private String nombrecontacto;
  
  @Size(max = 45)
  @Column(name = "telefonocontacto")
  private String telefonocontacto;

  @Size(max = 60)
  private String nombrerepresentantelegal;
  
  private String certificadosinpago;

  private Integer idenfasis;

  private String seleccion;

  @JoinColumn(name = "idenfasis", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne
  private Enfasis enfasis;
  
  public Empresa() {
  }

  public Empresa(Long id, @NotNull @Size(min = 1, max = 2) String tipodocumento,
		@NotNull @Size(min = 1, max = 16) String numerodocumento,
		@NotNull @Size(min = 1, max = 100) String nombre) {
      super();
      this.id = id;
      this.tipodocumento = tipodocumento;
      this.numerodocumento = numerodocumento;
      this.nombre = nombre;
  }
}
