package com.seguridadlimite.models.trabajador.application.EncodeFoto;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.util.EncodeFileToBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EncodeFotoTrabajadorService {
	private final EncodeFileToBase64 encodeFile;

	public void encodeFoto(Trabajador t) {
		Optional<String> base64 = encodeFile.encode("F", t.getId().toString());
		if (base64.isPresent()) {
			t.setBase64(base64.get());
			t.setFoto("S");
		} else {
			t.setBase64(null);
			t.setFoto("N");
		}
	}
}
