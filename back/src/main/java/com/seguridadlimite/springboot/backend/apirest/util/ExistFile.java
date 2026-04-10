package com.seguridadlimite.springboot.backend.apirest.util;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ExistFile {

	public Boolean existFile(String path, String tipo, Long id) {
		if ("F".equals(tipo)) {
			return existFotoTrabajador(path, id);
		}
		String fileName = path + tipo + id + ".png";
		File file = new File(fileName);
		return file.exists() && file.isFile();
	}

	/** Foto guardada como JPEG optimizado o legacy PNG. */
	private boolean existFotoTrabajador(String path, Long id) {
		String base = path + "F" + id;
		for (String ext : new String[] { "jpg", "jpeg", "png" }) {
			File file = new File(base + "." + ext);
			if (file.isFile()) {
				return true;
			}
		}
		return false;
	}
}
