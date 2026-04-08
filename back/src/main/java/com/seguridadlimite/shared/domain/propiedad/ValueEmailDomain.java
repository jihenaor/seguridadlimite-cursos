package com.seguridadlimite.shared.domain.propiedad;

import lombok.Getter;

/**
 * Padre de los value objects de tipo email
 *
 * @author andrvalo
 * @create 2021/03/08
 **/

public abstract class ValueEmailDomain extends ValueObjectDomain {
    @Getter
    private final String value;

    public ValueEmailDomain(String value) {
        super(value);
        this.value = value;
    }

    public ValueEmailDomain() {
        super();
        this.value = null;
    }

}
