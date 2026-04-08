package com.seguridadlimite.util;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static void copyAttributesFromEntity(Object source, Object target) {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        for (Field sourceField : sourceClass.getDeclaredFields()) {
            try {
                Field targetField = targetClass.getDeclaredField(sourceField.getName());
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                targetField.set(target, sourceField.get(source));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Ignorar si el campo no existe en la clase destino o no se puede acceder
            }
        }
    }
}