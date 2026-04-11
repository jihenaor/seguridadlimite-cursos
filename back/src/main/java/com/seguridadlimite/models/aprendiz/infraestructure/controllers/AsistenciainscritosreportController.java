package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.generarformatoasistencia.FormatoasistenciaService;
import com.seguridadlimite.models.entity.ReportePojo;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/aprendiz")
@RequiredArgsConstructor
public class AsistenciainscritosreportController {

	private final FormatoasistenciaService formatoasistenciaService;

	@GetMapping("/asistenciainscritosreport")
	public ReportePojo asistenciareport() throws JRException, FileNotFoundException, BusinessException {

		return null;// formatoasistenciaService.exporterAsistenciaReport(null);

	}
}
