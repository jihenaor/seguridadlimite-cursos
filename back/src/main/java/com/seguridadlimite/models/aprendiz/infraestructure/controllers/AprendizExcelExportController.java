package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.exportexcel.ExportAprendizExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aprendices")
@RequiredArgsConstructor
public class AprendizExcelExportController {

    private final ExportAprendizExcelService exportService;

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() {
        byte[] excelContent = exportService.exportToExcel();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "aprendices.xlsx");
        headers.setContentLength(excelContent.length);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(excelContent);
    }
}
