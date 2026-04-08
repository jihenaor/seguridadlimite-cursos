package com.seguridadlimite.models.aprendiz.application.importarAprendices;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class RegistroTrabajador {
    @CsvBindByName(column = "x")
    private String x;

    @CsvBindByName(column = "NOMBRE")
    private String nombre;

    @CsvBindByName(column = "CEDULA")
    private String cedula;

    @CsvBindByName(column = "CONTACTO")
    private String contacto;
}