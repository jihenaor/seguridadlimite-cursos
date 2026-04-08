package com.seguridadlimite.models.aprendiz.application.importarAprendices;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class RegistroPermiso {
    @CsvBindByName(column = "x")
    private String x;

    @CsvBindByName(column = "trabajo")
    private String TRABAJO;

    @CsvBindByName(column = "fecha inicial")
    private String fechaInicial;

    @CsvBindByName(column = "fecha")
    private String fecha;
    @CsvBindByName(column = "nivel")
    private String nivel;

    @CsvBindByName(column = "entrenador")
    private String entrenador;
}