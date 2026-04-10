package com.seguridadlimite.models.nivel.domain;

import com.seguridadlimite.models.programa.model.Programa;
import com.seguridadlimite.models.nivel.domain.dto.DiaDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sl_niveles")
@Data
@NoArgsConstructor
public class Nivel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String nombre;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "duraciontotal")
  private Integer duraciontotal;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String estado;

  @Basic(optional = false)
  @NotNull
  private Integer orden;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  private String tieneevaluacionconocimientos;

  @Basic(optional = false)
  @NotNull
  private Long idprograma;

  @JoinColumn(name = "idprograma", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne
  private Programa programa;

  @Column(name = "dias")
  private Integer dias;

  private String requierepermiso;

  @Transient
  private Integer numerogrupos = 1;


  @Transient
  private List<DiaDto> diasdiseno;

}
