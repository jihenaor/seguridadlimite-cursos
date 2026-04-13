package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.importarAprendices.ImportarAprendicesExcelService;
import com.seguridadlimite.models.aprendiz.application.importarAprendices.ImportarAprendicesExcelService.ImportResultado;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class ImportarAprendicesRestController {

    private final ImportarAprendicesExcelService importarAprendicesExcelService;

    @PostMapping("/upload")
    public ResponseEntity<ImportResultado> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            ImportResultado resultado = new ImportResultado();
            resultado.setError("No se ha seleccionado ningún archivo.");
            return ResponseEntity.badRequest().body(resultado);
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".xlsx")) {
            ImportResultado resultado = new ImportResultado();
            resultado.setError("El archivo debe ser un Excel (.xlsx).");
            return ResponseEntity.badRequest().body(resultado);
        }

        log.info("Iniciando importación desde archivo: {}", filename);
        ImportResultado resultado = importarAprendicesExcelService.importar(file);

        if (resultado.getError() != null) {
            return ResponseEntity.unprocessableEntity().body(resultado);
        }

        return ResponseEntity.ok(resultado);
    }
}
