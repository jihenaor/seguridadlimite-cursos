package com.seguridadlimite.shared.domain.query;

import com.seguridadlimite.shared.domain.propiedad.*;

import java.util.Optional;

public interface ResponseManejoInfo extends Response {

    /**
     * Retorna el valor del value object que debe heredar de ValueObjectDomain para evitar errores en caso de null
     * @param object
     * @return
     */
    static String obtenerValor(ValueObjectDomain object) {
        Optional<ValueObjectDomain> ciudadObjec = Optional.ofNullable(object);
        return ciudadObjec.map(ValueObjectDomain::getValue).orElse(null);
    }

    /**
     * Retorna el valor del value object que debe heredar de ValueObjectDomainEnum para evitar errores en caso de null
     * @param object
     * @return
     */
    static String obtenerValor(ValueEnumDomain object) {
        Optional<ValueEnumDomain> value = Optional.ofNullable(object);
        return value.map(valueEnumDomain -> valueEnumDomain.getValue().toString()).orElse(null);
    }

    /**
     * Retorna el valor del value object que debe heredar de ValueObjectDomainEnum para evitar errores en caso de null
     * OJO: se debe usar el metodo definido por lombok para acceder al valor (isValue)
     * @param object
     * @return
     */
    static boolean obtenerValor(ValueBooleanDomain object) {
        Optional<ValueBooleanDomain> value = Optional.ofNullable(object);
        return value.isPresent() ? value.get().isValue() : null;
    }

    /**
     * Retorna el valor del value object que debe heredar de ValueDoubleDomain para evitar errores en caso de null
     * @param object
     * @return
     */
    static double obtenerValor(ValueDoubleDomain object) {
        Optional<ValueDoubleDomain> value = Optional.ofNullable(object);
        return value.map(ValueDoubleDomain::getValue).orElse(0.0);
    }


    static String obtenerValor(ValueObligatorioObjectDomain object) {
        Optional<ValueObligatorioObjectDomain> value = Optional.ofNullable(object);
        return value.map(ValueObligatorioObjectDomain::getValue).orElse(null);
    }

    static String obtenerValor(ValueDateObjectDomain object) {
        Optional<ValueDateObjectDomain> value = Optional.ofNullable(object);
        return value.map(ValueDateObjectDomain::getValueTexto).orElse(null);
    }


    static int obtenerValor(ValueEnteroDomain object) {
        Optional<ValueEnteroDomain> ciudadObjec = Optional.ofNullable(object);
        return ciudadObjec.map(ValueEnteroDomain::getValue).orElse(0);
    }

    static Float obtenerValor(ValueFlotanteDomain object) {
        Optional<ValueFlotanteDomain> ciudadObjec = Optional.ofNullable(object);
        return ciudadObjec.map(ValueFlotanteDomain::getValue).orElse(0F);
    }

    static Long obtenerValor(ValueLargoDomain object) {
        Optional<ValueLargoDomain> ciudadObjec = Optional.ofNullable(object);
        return ciudadObjec.map(ValueLargoDomain::getValue).orElse(0L);
    }

    static String obtenerValor(ValueCorreoDomain object) {
        Optional<ValueCorreoDomain> correoObjec = Optional.ofNullable(object);
        return correoObjec.map(ValueCorreoDomain::getValue).orElse(null);
    }

    /**
     * Retorna el valor del value object que debe heredar de ValueObjectDomain para evitar errores en caso de null
     * @param object
     * @return
     */
    static String obtenerValor(ValueObjectDomain object, String valorAlternativo) {
        Optional<ValueObjectDomain> ciudadObjec = Optional.ofNullable(object);
        return ciudadObjec.map(ValueObjectDomain::getValue).orElse(valorAlternativo);
    }
}
