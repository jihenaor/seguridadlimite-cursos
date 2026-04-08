package com.seguridadlimite.models.aprendiz.application.importarAprendices;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Registro {
    @CsvBindByName(column = "x")
    private String x;

    @CsvBindByName(column = "trabajo")
    private String trabajo;

    @CsvBindByName(column = "codigo")
    private String codigo;

    @CsvBindByName(column = "fechainicial")
    private String fechaInicial;

    @CsvBindByName(column = "fecha")
    private String fecha;

    @CsvBindByName(column = "NOMBRE")
    private String nombre;

    @CsvBindByName(column = "cedula")
    private String cedula;
    @CsvBindByName(column = "NIVEL")
    private String nivel;

    @CsvBindByName(column = "nombreempresa")
    private String empresa;

    @CsvBindByName(column = "NIT")
    private String nit;

    @CsvBindByName(column = "contacto")
    private String contacto;

    @CsvBindByName(column = "EPS")
    private String eps;

    @CsvBindByName(column = "labor")
    private String labor;

    @CsvBindByName(column = "ENFASIS")
    private String enfasis;

    @CsvBindByName(column = "PAGADO")
    private String pagado;
    @CsvBindByName(column = "SALDO")
    private String saldo;
    @CsvBindByName(column = "ENTRENADOR")
    private String entrenador;


    @CsvBindByName(column = "pagado")
    private String faltaPago;
}