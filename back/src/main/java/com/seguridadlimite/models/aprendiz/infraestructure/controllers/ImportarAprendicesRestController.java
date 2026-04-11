package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.extern.slf4j.Slf4j;

import com.seguridadlimite.models.aprendiz.application.importarAprendices.ImportarAprendices;
import com.seguridadlimite.models.aprendiz.application.importarAprendices.ImportarAprendicesPermiso;
import com.seguridadlimite.models.aprendiz.application.importarAprendices.ImportarPermisoTrabajo;
import com.seguridadlimite.models.aprendiz.application.importarAprendices.ImportarTrabajador;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public	 class ImportarAprendicesRestController {

	private ImportarAprendices importarAprendices;

	private ImportarPermisoTrabajo importarPermisoTrabajo;

	private ImportarTrabajador importarTrabajador;

    private ImportarAprendicesPermiso importarAprendicesPermiso;

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "No se ha seleccionado un archivo para cargar";
		}

		try {
			String content = new String(file.getBytes());

//			importarPermisoTrabajo.index(content);
//			importarTrabajador.index(content);
//			importarAprendices.index(content);
            importarAprendicesPermiso.index(content);
			return "Archivo cargado con éxito: " + file.getOriginalFilename();
		} catch (IOException e) {
			return "Error al cargar el archivo: " + e.getMessage();
		} catch (BusinessException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
