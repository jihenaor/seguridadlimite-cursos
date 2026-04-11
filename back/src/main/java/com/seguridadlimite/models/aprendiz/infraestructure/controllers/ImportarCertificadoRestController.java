package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.importarCertificados.ImportarCertificado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImportarCertificadoRestController {

	private final ImportarCertificado importarCertificado;


	@PostMapping("/uploadCertificado")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "No se ha seleccionado un archivo para cargar";
		}

		try {
			String fileName = file.getOriginalFilename();

			importarCertificado.saveFile(fileName, file);

			return "Archivo cargado con éxito: " + fileName;
		} catch (IOException e) {
			return "Error al cargar el archivo: " + e.getMessage();
		}
    }
}
