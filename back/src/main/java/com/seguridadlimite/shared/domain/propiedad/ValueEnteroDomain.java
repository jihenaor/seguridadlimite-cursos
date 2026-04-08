package com.seguridadlimite.shared.domain.propiedad;

import lombok.Getter;

import java.io.Serializable;
/**
 * Value object padre de todos los value objects tipo entero
 *
 * @author luisa.munoz
 * @create 29/12/2020
 **/
public abstract class ValueEnteroDomain implements Serializable {
    @Getter
    protected final int value;

    public ValueEnteroDomain(int value) {
        this.value = value;
    }

    public ValueEnteroDomain() {
        this.value = 0;
    }
}