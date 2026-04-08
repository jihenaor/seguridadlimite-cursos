package com.seguridadlimite.models.entity;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.personal.dominio.Personal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Perfil {
  private String numerodocumento;
  
  private String jwt;
  
  private String perfil; //1 profesor 2 estudiante
  
  private Aprendiz aprendiz;
  
  private Personal personal;

  public Perfil() {
  }

  public String getNumerodocumento() {
    return numerodocumento;
  }

  public void setNumerodocumento(String numerodocumento) {
    this.numerodocumento = numerodocumento;
  }

  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }


  
}
