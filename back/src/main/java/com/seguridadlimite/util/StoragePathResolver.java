package com.seguridadlimite.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Resuelve directorios de almacenamiento según SO y overrides opcionales en Windows.
 */
@Component
public class StoragePathResolver {

	public boolean isWindows() {
		String os = System.getProperty("os.name", "");
		return os.toLowerCase().contains("win");
	}

	/**
	 * @param primary    ruta por defecto (Linux, Docker, o Windows con {@code C:/...})
	 * @param winOverride valor de {@code path.windows.*}; si hay texto en Windows, tiene prioridad
	 */
	public String resolveDirectory(String primary, String winOverride) {
		String raw = primary;
		if (isWindows() && StringUtils.hasText(winOverride)) {
			raw = winOverride.trim();
		} else if (StringUtils.hasText(primary)) {
			raw = primary.trim();
		}
		if (!StringUtils.hasText(raw)) {
			throw new IllegalArgumentException("Ruta de almacenamiento no configurada");
		}
		Path absolute = Paths.get(raw).toAbsolutePath().normalize();
		String s = absolute.toString();
		if (!s.endsWith(File.separator)) {
			s = s + File.separator;
		}
		return s;
	}
}
