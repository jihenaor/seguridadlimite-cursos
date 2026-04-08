package com.seguridadlimite.util;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

public class UppercaseInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(
            Object entity, Serializable id, Object[] state,
            String[] propertyNames, Type[] types) {

        if (entity.getClass().isAnnotationPresent(UppercaseTransform.class)) {
            for (int i = 0; i < state.length; i++) {
                if (state[i] instanceof String) {
//                    state[i] = ((String) state[i]).toUpperCase();
                }
            }
        }

        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        for (int i = 0; i < propertyNames.length; i++) {
            if (currentState[i] instanceof String) {
//                currentState[i] = ((String) currentState[i]).toUpperCase();
            }
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

}
