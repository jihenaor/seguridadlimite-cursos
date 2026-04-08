package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.UploadCertificado.UploadCertificadoUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/certificado")
@Slf4j
public class CertificadoController {
    private final UploadCertificadoUseCase uploadCertificadoUseCase;

    public CertificadoController(UploadCertificadoUseCase uploadCertificadoUseCase) {
        this.uploadCertificadoUseCase = uploadCertificadoUseCase;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadCertificado(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idaprendiz") Long idAprendiz) {
        log.info("Inicia upload de certificado. Id aprendiz: " + idAprendiz);

        uploadCertificadoUseCase.uploadCertificado(file, idAprendiz);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Certificado subido correctamente");

        return ResponseEntity.ok(response);
    }

}