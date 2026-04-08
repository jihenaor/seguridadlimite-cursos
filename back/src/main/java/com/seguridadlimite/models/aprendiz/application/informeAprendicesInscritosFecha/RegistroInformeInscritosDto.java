package com.seguridadlimite.models.aprendiz.application.informeAprendicesInscritosFecha;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegistroInformeInscritosDto {
    private long idaprendiz;
    private String tipoDocumento;
    private String documento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String genero;
    private String paisNacimiento;
    private String fechaNacimiento;
    private String nivelEducativo;
    private String cargoActual;
    private String sector;
    private String empleador;
    private String arl;
}