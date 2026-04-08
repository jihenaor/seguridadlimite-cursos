package com.seguridadlimite.models.aprendiz.application.updateSignature.infraestructure.controller;

import com.seguridadlimite.models.aprendiz.application.updateSignature.application.IUpdateSignatureService;
import com.seguridadlimite.models.entity.FirmaAprendiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/aprendiz")
@Slf4j
public class UpdateSignatureController {

	@Autowired
	@Qualifier("updateSignatureServiceImpl")
	private IUpdateSignatureService service;

	@PostMapping(path = "/updateSignature", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> updateFirma(@RequestBody FirmaAprendiz entity) throws IOException {

        try {
            service.saveFirma(entity);
            log.info("Firma actualizada exitosamente para aprendiz ID: {}", entity.getId());

            return new ResponseEntity<>(
                    "{\"status\" : \"ok\"}",
                      HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al actualizar firma para aprendiz ID: {}", entity.getId(), e);
            throw e;
        }
	}
}
