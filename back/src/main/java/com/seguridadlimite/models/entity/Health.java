package com.seguridadlimite.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Health {
    public String estadoServidorExterno;

    public String estadoServidorInterno;

    public String estadoBaseDatosSicev;
}
