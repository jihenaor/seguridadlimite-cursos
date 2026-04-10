package com.seguridadlimite.springboot.backend.apirest.util;

import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.util.PathConfig;
import com.seguridadlimite.util.StoragePathResolver;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
@Data
public class GetPathFiles {

	private String fotosPath;
	private String signaturesPath;
	private String certificadosPath;
	private String documentsPath;

	private final PathConfig pathConfig;
	private final StoragePathResolver storagePathResolver;

	public GetPathFiles(PathConfig pathConfig, StoragePathResolver storagePathResolver) {
		this.pathConfig = pathConfig;
		this.storagePathResolver = storagePathResolver;
	}

	@PostConstruct
	private void initializePaths() {
		if (!StringUtils.hasText(pathConfig.getFotos())
				|| !StringUtils.hasText(pathConfig.getSignatures())
				|| !StringUtils.hasText(pathConfig.getCertificados())
				|| !StringUtils.hasText(pathConfig.getDocuments())) {
			throw new BusinessException(
					"Configure path.fotos, path.signatures, path.certificados y path.documents (o variables PATH_*).");
		}

		PathConfig.WindowsPaths win = pathConfig.getWindows() != null ? pathConfig.getWindows() : new PathConfig.WindowsPaths();

		this.fotosPath = storagePathResolver.resolveDirectory(pathConfig.getFotos(), win.getFotos());
		this.signaturesPath = storagePathResolver.resolveDirectory(pathConfig.getSignatures(), win.getSignatures());
		this.certificadosPath = storagePathResolver.resolveDirectory(pathConfig.getCertificados(), win.getCertificados());
		this.documentsPath = storagePathResolver.resolveDirectory(pathConfig.getDocuments(), win.getDocuments());

		log.info(
				"Rutas de almacenamiento ({}): fotos={}, signatures={}, certificados={}, documents={}",
				storagePathResolver.isWindows() ? "Windows" : "no Windows",
				fotosPath,
				signaturesPath,
				certificadosPath,
				documentsPath);
	}
}
