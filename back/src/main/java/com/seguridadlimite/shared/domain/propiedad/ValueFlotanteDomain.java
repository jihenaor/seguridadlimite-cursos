package com.seguridadlimite.shared.domain.propiedad;

import lombok.Getter;

/**
 * Padre de los value objects de tipo float
 */
public abstract class ValueFlotanteDomain {
    @Getter
    protected final float value;

    public ValueFlotanteDomain(float value) {
        this.value = value;
    }

    public ValueFlotanteDomain() {
        this.value = 0;
    }
}
