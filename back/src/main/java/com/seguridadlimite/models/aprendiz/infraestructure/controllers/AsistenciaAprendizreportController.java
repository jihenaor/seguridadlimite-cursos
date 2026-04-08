package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.generarformatoasistencia.FormatoasistenciaAprendizService;
import com.seguridadlimite.models.entity.ReportePojo;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AsistenciaAprendizreportController {

	@Autowired
	private FormatoasistenciaAprendizService formatoasistenciaAprendizService;


	@GetMapping("/{idaprendiz}/asistenciaaprendizreport")
	public ReportePojo asistenciaAprendizreport(@PathVariable Long idaprendiz) throws JRException, IOException, BusinessException {

		return formatoasistenciaAprendizService.exporterAsistenciaReport(idaprendiz);

	}
}
