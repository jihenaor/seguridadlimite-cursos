package com.seguridadlimite.models.trabajador.application.EncodeFoto;

import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.util.ExistFile;
import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import org.springframework.stereotype.Service;

@Service

public class ValidarExisteFotoTrabajadorService {

	private final ExistFile existFile;
	private final GetPathFiles getPathFiles;

	public ValidarExisteFotoTrabajadorService(ExistFile existFile, GetPathFiles getPathFiles) {
		this.existFile = existFile;
		this.getPathFiles = getPathFiles;
	}

	public void exist(Trabajador t) {
		t.setFoto(Boolean.TRUE.equals(existFile.existFile(getPathFiles.getFotosPath(),
				"F", t.getId())) ? "S" : "N");
	}
}
