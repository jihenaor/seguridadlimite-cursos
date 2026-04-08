package com.seguridadlimite.models.nivel.domain;

public enum NivelEnum{
    TRABAJADOR_AUTORIZADO(1, "TRABAJADOR AUTORIZADO"),
    COORDINADOR(2, "COORDINADOR"),
    BASICO_OPERATIVO(3, "BASICO OPERATIVO (SALE)"),
    REENTRENAMIENTO(4, "REENTRENAMIENTO"),
    ACTUALIZACION_COORDINADOR(5, "ACTUALIZACION DE COORDINADOR"),
    JEFE_DE_AREA(6, "JEFE DE AREA PARA TRABAJO EN ALTURAS");

    private int codigo;
    private String descripcion;

    NivelEnum(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
