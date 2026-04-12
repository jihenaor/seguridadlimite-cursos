package com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.entity.Trabajadordocumento;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seguridadlimite.util.StorageDirectories;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UpdateDocumentoTrabajadorService {

	@Value(value = "${path.documents:/root/images/documents/}")
	private String documentsPath;

	private final ITrabajadorDao dao;

	@Transactional(readOnly = true)
	public Trabajador findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Transactional
	public void saveDocumentotrabajador(Trabajadordocumento t) throws IOException {
		Trabajador trabajador = findById(t.getId());
		
		trabajador.setAdjuntodocumento("S");
		trabajador.setExt(t.getExt());
		
		saveFile(t.getId(), t.getBase64a(), "A", t.getExt());
		saveFile(t.getId(), t.getBase64b(), "B", t.getExt());
		
		dao.save(trabajador);
	}

	private void saveFile(Long id, String base64, String lado, String ext) {
		if (base64 == null || base64.isEmpty()) {
			return;
		}
		byte[] decoded = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
		Path target = StorageDirectories.resolveUnder(documentsPath, "D" + lado + id + "." + ext);
		try {
			StorageDirectories.writeBytes(target, decoded);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
