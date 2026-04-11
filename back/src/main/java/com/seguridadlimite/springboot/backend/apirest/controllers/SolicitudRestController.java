package com.seguridadlimite.springboot.backend.apirest.controllers;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seguridadlimite.models.entity.Solicitud;
import com.seguridadlimite.springboot.backend.apirest.services.MailServiceImpl;
import com.seguridadlimite.springboot.backend.apirest.services.SolicitudServiceImpl;

@RequiredArgsConstructor
@Slf4j
public class SolicitudRestController extends Controller {

	private final SolicitudServiceImpl service;
	

	@Autowired
	MailServiceImpl mailService;
	
	@GetMapping("/solicitudes/{aucodestad}")
	public List<Solicitud> index(@PathVariable String aucodestad) {
		return service.findAll(aucodestad);
	}

	@PostMapping(path = "saveSolicitud", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public Solicitud create(@RequestBody Solicitud entity) {
		Solicitud g = null;
		
		try {
			if (entity.getId() == null) {
				entity.setCreateAt(new Date());
			}
			entity.setUpdateAt(new Date());
			
			if (entity.getId() == null) {
				g = service.save(entity);
			} else {
				/*
				g = service.findById(entity.getId());
				
				try {
					copiarEntidad(entity, g, false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					log.error("Se capturó una excepción: ", e1);
				}
				if (g.getCreateAt() == null) {
					g.setCreateAt(new Date());
				}
				service.save(g);
				*/
			}
		} catch (Exception e) {
			mailService.sendException("saveSolicitud", e);
		}
//		revisar celular llega mal
		try {
			String texto = "Documento:" + entity.getNumerodocumento() + "<br />" +
					 "Persona contacto: " + entity.getNombrecontacto() + "<br />" +
					 "Email:" + entity.getEmail() + "<br />" +
					 "Celular: " + entity.getCelular() + "<br />" +
					 "Horario: " + entity.getHorario() + "<br />" +
					 "Cupo solicitado: " + entity.getCupo() + "<br />" +
					 "Programa: "+ entity.getPrograma() + "<br />" +
					 "Nivel: " + entity.getNivel()  + "<br />" +
					 "Empresa: " + entity.getEmpresa() + "<br />";
			
			mailService.send("auxadministrativo@seguridadallimite.com",
					"auxadministrativo@seguridadallimite.com",
					"Solicitud de información",
					texto);
		} catch (Exception e) {
			mailService.sendException("saveSolicitudEnviacorreo", e);
		}
		
		return g;
	}
}
