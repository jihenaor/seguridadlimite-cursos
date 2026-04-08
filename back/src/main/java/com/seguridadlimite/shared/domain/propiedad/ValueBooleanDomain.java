package com.seguridadlimite.shared.domain.propiedad;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Value object padre de todos los value objects tipo boolean
 *
 * @author carlos.guzman
 * @create 7/01/2021
 **/
@Getter
@RequiredArgsConstructor
public abstract class ValueBooleanDomain {
    protected final boolean value;
}
