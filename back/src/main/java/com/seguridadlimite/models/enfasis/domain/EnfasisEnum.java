package com.seguridadlimite.models.enfasis.domain;

import java.util.List;
import java.util.Optional;

public enum EnfasisEnum {
    HIDROCARBUROS(1, "HIDROCARBUROS"),
    TELECOMUNICACIONES(2, "TELECOMUNICACIONES"),
    CONSTRUCCION(3, "CONSTRUCCION"),
    ELECTRICO(4, "ELECTRICO"),
    AGROPECUARIO(5, "AGROPECUARIO"),
    COMERCIO_Y_SERVICIOS(6, "COMERCIO Y SERVICIOS"),
    INDUSTRIAL(7, "INDUSTRIAL"),
    TRANSPORTE(8, "TRANSPORTE"),
    COMERCIO(9, "COMERCIO"),
    FINANCIERO(10, "FINANCIERO"),
    MINERO_Y_ENERGETICO(11, "MINERO Y ENERGETICO"),
    EDUCACION(12, "EDUCACION"),
    OTRO(99, "OTRO");

    private int codigo;
    private String nombre;

    EnfasisEnum(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public static String getNombrePorCodigo(List<Enfasis> enfasisList, int codigo) {
        Optional<String> nombreOptional = enfasisList.stream()
                .filter(enfasis -> enfasis.getId() == codigo)
                .map(Enfasis::getNombre)
                .findFirst();

        return nombreOptional.orElse("Código no encontrado");
    }


}
