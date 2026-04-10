package com.seguridadlimite.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "path")
@Data
public class PathConfig {

	private String fotos;
	private String signatures;
	private String certificados;
	private String documents;

	/**
	 * Rutas opcionales en Windows ({@code C:\...} en YAML como {@code C:\\...}).
	 * Si cada campo está vacío, se usa la ruta principal correspondiente (también válida en Windows con {@code C:/...}).
	 */
	private WindowsPaths windows = new WindowsPaths();

	@Data
	public static class WindowsPaths {
		private String fotos = "";
		private String signatures = "";
		private String documents = "";
		private String certificados = "";
	}
}
