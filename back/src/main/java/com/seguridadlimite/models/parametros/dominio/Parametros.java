
package com.seguridadlimite.models.parametros.dominio;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sl_parametros")
@Data
public class Parametros implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  private String nit;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat
          (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "es-CO", timezone = "America/Bogota")
  private Date fechaevaluacion;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat
          (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "es-CO", timezone = "America/Bogota")
  private Date fechaencuesta;

  private String test;

  int horasevaluacion;

}
