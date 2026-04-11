package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.generarcertificado.GenerarCertificadoService;
import com.seguridadlimite.models.entity.ReportePojo;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CertificadoQueryController {

	private final GenerarCertificadoService generarCertificadoService;

	@GetMapping("/certificado/{idaprendiz}")
	public ReportePojo certificadoReport(@PathVariable Long idaprendiz) throws JRException, FileNotFoundException, BusinessException {

		return generarCertificadoService.generar(idaprendiz);

	}
}
