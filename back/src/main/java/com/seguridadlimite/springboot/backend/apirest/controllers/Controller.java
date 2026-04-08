package com.seguridadlimite.springboot.backend.apirest.controllers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Controller {

	public void copiarEntidad(Object copy, Object paste, Boolean onlyWithValue) throws NoSuchMethodException, Exception {
		Field[] fields = copy.getClass().getDeclaredFields();
		// Field[] fields1 = paste.getClass().getDeclaredFields();
		Method setter;
		Method getter;
		Object value;
		String nombreCampo;

		if (paste == null) {
			throw new Exception("La entidad paste es null en copiarEntidad");
		}
		if (copy == null) {
			throw new Exception("La entidad copy es null en copiarEntidad");
		}

		for (Field field : fields) {
			nombreCampo = String.valueOf(field.getName().charAt(0)).toUpperCase() + field.getName().substring(1);

			if (nombreCampo.equals("id") 
					|| nombreCampo.equals("SerialVersionUID")
					|| nombreCampo.equals("nombrecompleto") 
					|| nombreCampo.equals("Id")) {
				continue;
			}

			try {

				try {
					getter = copy.getClass().getMethod("get" + nombreCampo);
					value = getter.invoke(copy, new Object[0]);
				} catch (NoSuchMethodException ex) {
					continue;
				} catch (Exception e) {
					System.out.println("" + nombreCampo);
					throw e;
				}

				try {
					final Class<?> type = field.getType();
					if (field.getType().toString().contains("Collection")) {

					} else {
						if (nombreCampo.equals("uacod") || nombreCampo.equals("uccod")) {
							int x = 0;
							x++;
						} else {
							setter = paste.getClass().getMethod("set" + nombreCampo, field.getType());
							if (setter != null) {
								if (onlyWithValue) {
									if (value != null) {
										setter.invoke(paste, value);
									}
								} else {
									setter.invoke(paste, value);
								}
							}
						}
					}

				} catch (NoSuchMethodException ex) {
					System.out.println("Error copiando el campo " + nombreCampo);
					continue;
				}

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| SecurityException ex) {
				// ex.printStackTrace();
				continue;
			}
		}
	}
}
