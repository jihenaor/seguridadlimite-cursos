package com.seguridadlimite.shared.domain.propiedad;

import lombok.Getter;

/**
 * Padre de los value objects de tipo double
 */
public abstract class ValueDoubleDomain {
    @Getter
    protected final double value;

    public ValueDoubleDomain(double value) {
        this.value = value;
    }

    public ValueDoubleDomain() {
        this.value = 0;
    }
}
