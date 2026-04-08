package com.seguridadlimite.shared.domain.propiedad;

import lombok.Getter;

import java.io.Serializable;

public abstract class ValueLargoDomain implements Serializable {
    @Getter
    protected final long value;

    public ValueLargoDomain(long value) {
        this.value = value;
    }

    public ValueLargoDomain() {
        this.value = 0;
    }
}
