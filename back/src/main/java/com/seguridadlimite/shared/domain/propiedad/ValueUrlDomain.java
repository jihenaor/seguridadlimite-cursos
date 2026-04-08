package com.seguridadlimite.shared.domain.propiedad;

import lombok.Getter;

public abstract class ValueUrlDomain extends ValueObjectDomain {
    @Getter
    protected final String value;

    public ValueUrlDomain(String value) {
        super(value);
        this.value = value;
    }

    public ValueUrlDomain() {
        this.value = null;
    }
}
