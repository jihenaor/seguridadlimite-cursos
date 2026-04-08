package com.seguridadlimite.shared.domain.propiedad;

import lombok.Getter;

/**
 * Value object padre de todos los value objects tipo byte[]
 *
 * @author carlos.guzman
 * @create 6/01/2021
 **/
public abstract class ValueByteDomain {
    @Getter
    protected final byte[] value;

    public ValueByteDomain(byte[] value) {
        this.value = value;
    }
}
