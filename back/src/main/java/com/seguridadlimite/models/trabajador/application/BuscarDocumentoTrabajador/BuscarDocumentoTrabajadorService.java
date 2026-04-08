package com.seguridadlimite.models.trabajador.application.BuscarDocumentoTrabajador;

import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.util.EncodeFileToBase64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BuscarDocumentoTrabajadorService {

	@Value(value = "${path.documents:/root/images/documents/}")
	private String documentsPath;

	private final EncodeFileToBase64 encodeFileToBase64;

	public BuscarDocumentoTrabajadorService(EncodeFileToBase64 encodeFileToBase64) {
		this.encodeFileToBase64 = encodeFileToBase64;
	}

	public void getDocumentoLadoALadoB(Trabajador t)  {
		if (t.getExt() == null || t.getExt().isEmpty()) {
			t.setAdjuntodocumento("N");
			return;
		}

		String documentoLadoA = encoder(t, "A");

		if (documentoLadoA.isEmpty()) {
			t.setExt(null);
			t.setAdjuntodocumento("N");
		} else {
			t.setBase64a(encoder(t, "A"));
		}

		t.setBase64b(encoder(t, "B"));
    }

	private String encoder(Trabajador t, String lado){
		String fileName = documentsPath + "D"
				+ lado
				+ t.getId()
				+ "." + t.getExt();

		return encodeFileToBase64.encode(fileName);
	}
}
