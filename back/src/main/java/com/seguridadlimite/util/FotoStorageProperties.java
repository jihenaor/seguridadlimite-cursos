package com.seguridadlimite.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Parámetros de almacenamiento y compresión de fotos de trabajador/aprendiz.
 * Configurables en YAML bajo {@code app.storage.foto.*} o variables de entorno.
 */
@Component
@ConfigurationProperties(prefix = "app.storage.foto")
@Data
public class FotoStorageProperties {

	/** Lado máximo (ancho o alto) en píxeles; se escala manteniendo la proporción. */
	private int maxEdgePx = 960;

	/** Calidad JPEG entre 0 y 1 (mayor = más peso). */
	private float jpegQuality = 0.82f;
}
