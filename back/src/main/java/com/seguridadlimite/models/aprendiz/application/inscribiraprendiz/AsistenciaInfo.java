package com.seguridadlimite.models.aprendiz.application.inscribiraprendiz;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AsistenciaInfo {
    private String numerodocumento;
    private String nombrenivel;
    private String modulo;
    private String unidad;
    private String fecha;
    private String formacion;
    private String entrenamiento;
    private String dia;
    private String firmaaprendiz;
    private String nombreentrenador;
    private String nombresupervisor;
    private String nombreaprendiz;
    private long idgrupo;
    private String enfasis;
    private String evaluacionconocimiento;
    private String evaluacionhabilidades;
    private String otrostalleres;
    private String permisoalturas;
    private String personaldeapoyo;
    private String logoseguridad;
}
