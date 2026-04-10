package com.seguridadlimite.models.aprendiz.application.guardarFotoTrabajador;

import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.entity.Fototrabajador;
import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import com.seguridadlimite.util.FotoImageOptimizer;
import com.seguridadlimite.util.FotoStorageProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
@AllArgsConstructor
@Slf4j
public class GuardarFotoTrabajadorService {

	private static final String FOTO_PREFIX = "F";
	private static final String FOTO_EXT_JPG = ".jpg";

	private final GetPathFiles getPathFiles;
	private final ITrabajadorDao trabajadorDao;
	private final FotoImageOptimizer fotoImageOptimizer;
	private final FotoStorageProperties fotoStorageProperties;

	@Transactional
	public void guardar(Fototrabajador f) throws IOException {
		int poscoma = f.getBase64().indexOf("base64,");

		if (poscoma > 0) {
			f.setBase64(f.getBase64().substring(poscoma + 7));
		}

		byte[] decodedString = Base64.getDecoder().decode(f.getBase64().getBytes(StandardCharsets.UTF_8));

		Path dir = Path.of(getPathFiles.getFotosPath());
		Files.createDirectories(dir);

		String baseName = FOTO_PREFIX + f.getId();
		Path rutaArchivo = dir.resolve(baseName + FOTO_EXT_JPG);

		byte[] bytesToWrite;
		try {
			bytesToWrite = fotoImageOptimizer.optimizeToJpeg(
					decodedString,
					fotoStorageProperties.getMaxEdgePx(),
					fotoStorageProperties.getJpegQuality());
		} catch (Exception e) {
			log.warn("No se pudo optimizar a JPEG (id={}), se guarda el binario original: {}", f.getId(), e.getMessage());
			bytesToWrite = decodedString;
			String ext = f.getExt() == null ? "png" : f.getExt();
			rutaArchivo = dir.resolve(baseName + "." + ext);
		}

		try (FileOutputStream fos = new FileOutputStream(rutaArchivo.toFile())) {
			fos.write(bytesToWrite);
			trabajadorDao.actualizarFoto(f.getId(), "S");
			log.info("Foto guardada: {} (trabajador {})", rutaArchivo.toAbsolutePath(), f.getId());
		} catch (IOException e) {
			log.error("Error al guardar la foto en: {}", rutaArchivo, e);
			throw e;
		}

		// Evita duplicados al migrar de PNG a JPEG
		if (rutaArchivo.toString().endsWith(FOTO_EXT_JPG)) {
			Path legacyPng = dir.resolve(baseName + ".png");
			try {
				Files.deleteIfExists(legacyPng);
			} catch (IOException ex) {
				log.debug("No se pudo eliminar foto legacy PNG: {}", legacyPng, ex);
			}
		}
	}
}
