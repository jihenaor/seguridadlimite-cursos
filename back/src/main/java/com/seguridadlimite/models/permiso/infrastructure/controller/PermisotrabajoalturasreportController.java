package com.seguridadlimite.models.permiso.infrastructure.controller;

import com.seguridadlimite.models.permiso.application.service.PermisoTrabajoAlturasService;
import com.seguridadlimite.models.entity.ReportePojo;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PermisotrabajoalturasreportController {

	@Autowired
	private PermisoTrabajoAlturasService permisoTrabajoAlturasService;

	@GetMapping("/{idPermiso}/{grupo}/permisotrabajoalturas")
	public ReportePojo asistenciareport(
			@PathVariable Integer idPermiso,
			@PathVariable Integer grupo) throws JRException, BusinessException {

		return permisoTrabajoAlturasService.exporterPermisoTrabajoAlturasReport(idPermiso, grupo);
	}
}
