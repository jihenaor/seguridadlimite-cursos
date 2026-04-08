package com.seguridadlimite.models.aprendiz.application.guardarFotoTrabajador;

import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.entity.Fototrabajador;
import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@AllArgsConstructor
@Slf4j
public class GuardarFotoTrabajadorService {

	private final GetPathFiles getPathFiles;
	private final ITrabajadorDao trabajadorDao;

	@Transactional
	public void guardar(Fototrabajador f) throws IOException {
		int poscoma = f.getBase64().indexOf("base64,");

		if (poscoma > 0) {
			f.setBase64(f.getBase64().substring(poscoma + 7));
		}

		byte[] decodedString = Base64.getDecoder().decode(f.getBase64().getBytes(StandardCharsets.UTF_8));

		// Construir la ruta completa del archivo
		String rutaArchivo = getPathFiles.getFotosPath() + "F" + f.getId() + (f.getExt() == null ? ".png" : "." + f.getExt());

		// Guardar el archivo en la ruta
		try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
			fos.write(decodedString);
			trabajadorDao.actualizarFoto(f.getId(), "S");
			log.info("📸 Foto guardada exitosamente en: {}. trabajador {}", rutaArchivo, f.getId());
		} catch (IOException e) {
			log.error("❌ Error al guardar la foto en: {}", rutaArchivo, e);
			throw e;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
