package com.seguridadlimite.models.aprendiz.domain;


import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;


@Builder
@Getter
public class AprendizCertificadoDTO implements Serializable {
  private long id;
  private String tipodocumento;

  private String numerodocumento;
  private String primernombre;
  private String segundonombre;
  private String primerapellido;
  private String segundoapellido;
  private String nombreprograma;
  private String codigoverificacion;
  private String nivelnombre;
  private String fechafinal;
  private String pagocurso;
}
