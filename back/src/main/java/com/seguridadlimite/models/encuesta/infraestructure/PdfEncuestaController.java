package com.seguridadlimite.models.encuesta.infraestructure;

import com.seguridadlimite.models.encuesta.application.GenerarPdfEncuestaService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/encuesta")
@AllArgsConstructor
public class PdfEncuestaController {

    private final GenerarPdfEncuestaService generarPdfEncuestaService;

    @GetMapping("/pdf/{idaprendiz}")
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable long idaprendiz) {
        ByteArrayInputStream bis = generarPdfEncuestaService.execute(idaprendiz);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=encuesta.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}