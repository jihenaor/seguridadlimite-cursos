package com.seguridadlimite.models.aprendiz.application.importarCertificados;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Registro {
    @CsvBindByName(column = "x")
    private String x;

    @CsvBindByName(column = "IDMIN")
    private String idMinTrabajo;

    @CsvBindByName(column = "CODIGO")
    private String codigo;

    @CsvBindByName(column = "fecha inicial")
    private String fechaInicial;
    @CsvBindByName(column = "FECHA")
    private String fecha;
    @CsvBindByName(column = "NOMBRE")
    private String nombre;
    @CsvBindByName(column = "CEDULA")
    private String cedula;
    @CsvBindByName(column = "NIVEL")
    private String nivel;
    @CsvBindByName(column = "EPS")
    private String eps;
    @CsvBindByName(column = "ENFASIS")
    private String enfasis;
    @CsvBindByName(column = "LABOR")
    private String labor;
    @CsvBindByName(column = "# CONTACTO")
    private String contacto;
    @CsvBindByName(column = "EMPRESA")
    private String empresa;
    @CsvBindByName(column = "NIT")
    private String nit;
    @CsvBindByName(column = "PAGADO")
    private String pagado;
    @CsvBindByName(column = "SALDO")
    private String saldo;
    @CsvBindByName(column = "ENTRENADOR")
    private String entrenador;

    @CsvBindByName(column = "comentario")
    private String comentario;
    @CsvBindByName(column = "falta pago")
    private String faltaPago;
}