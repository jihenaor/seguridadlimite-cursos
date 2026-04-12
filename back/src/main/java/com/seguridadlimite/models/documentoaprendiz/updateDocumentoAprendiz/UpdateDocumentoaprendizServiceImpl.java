package com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz;

import com.seguridadlimite.models.dao.IDocumentoaprendizDao;
import com.seguridadlimite.models.documentoaprendiz.domain.Documentoaprendiz;
import com.seguridadlimite.models.documentos.domain.Documento;
import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import com.seguridadlimite.util.StorageDirectories;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class UpdateDocumentoaprendizServiceImpl {

	private final IDocumentoaprendizDao dao;
	private final GetPathFiles getPathFiles;

	@Transactional
	public void save(List<Documento> documentos) {
		for (Documento documento : documentos) {
			if (documento.getIdaprendiz() == 0) {
				return;
			}
		}
		for (Documento documento : documentos) {
			save(documento);
		}
	}

	@Transactional
	private void save(Documento documento) {
		if (documento.getBase64() != null && !documento.getBase64().isEmpty()) {
			Documentoaprendiz t;

			t = dao.findByIdDocumentoIdaprendiz(documento.getId(), documento.getIdaprendiz());

			if (t == null) {
				t = new Documentoaprendiz();
				t.setIddocumento(documento.getId());
				t.setIdaprendiz(documento.getIdaprendiz());
				t.setExt(documento.getExt());
				t.setDocumentokey("XX");
			}

			t = dao.save(t);
			saveFile(t, documento);
			documento.setSavedmsg("Actualización exitosa");
		} else {
			documento.setSavedmsg("No se adjuntó ningún archivo");
		}
	}

	private void saveFile(Documentoaprendiz documentoaprendiz, Documento documento) {
		byte[] decodedString = Base64.getDecoder().decode(documento.getBase64().getBytes(StandardCharsets.UTF_8));
		Path target = StorageDirectories.resolveUnder(
				getPathFiles.getDocumentsPath(),
				"A" + documentoaprendiz.getId() + "." + documento.getExt());
		try {
			StorageDirectories.writeBytes(target, decodedString);
		} catch (IOException e) {
			throw new RuntimeException("Error al guardar el archivo: " + e.getMessage(), e);
		}
	}


}