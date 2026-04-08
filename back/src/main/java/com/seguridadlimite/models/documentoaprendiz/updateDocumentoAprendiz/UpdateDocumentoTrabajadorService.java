package com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz;

import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.entity.Trabajadordocumento;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class UpdateDocumentoTrabajadorService {

	@Value(value = "${path.documents:/root/images/documents/}")
	private String documentsPath;

	@Autowired
	private ITrabajadorDao dao;

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

	private void saveFile(Long id, String base64,
						  String lado,
						  String ext)  {
		if (base64 != null && !base64.isEmpty()) {
			byte[] decodedString = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));

			try (FileOutputStream fos = new FileOutputStream(documentsPath + "D"
					+ lado
					+ id + "." + ext)) {
				fos.write(decodedString);
				//fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
			} catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
	}
}
