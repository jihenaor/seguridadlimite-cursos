package com.seguridadlimite.shared.domain.propiedad;

import lombok.Getter;

/**
 * Value object padre de todos los value objects tipo enum
 *
 * @author carlos.guzman
 * @create 4/01/2021
 **/
public abstract class ValueEnumDomain<T> {
    @Getter
    protected final T value;

    public ValueEnumDomain(T value) {
        this.value = value;
    }

    public ValueEnumDomain(String value) {
        this.value = (T) value;
    }

    public ValueEnumDomain() {
        this.value = null;
    }
}
