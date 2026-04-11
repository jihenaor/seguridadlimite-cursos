package com.seguridadlimite.springboot.backend.apirest.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.AprendizServicerImpl;
import com.seguridadlimite.models.aprendiz.application.generarFormatoInscripcion.GenerarFormatoInscripcionService;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.asistencia.application.RegisterAsistenciaAprendizService;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.entity.*;
import com.seguridadlimite.springboot.backend.apirest.services.*;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AprendizController {

	private final AprendizServicerImpl servicer;
	
	private final RegisterAsistenciaAprendizService asistenciaService;

	private final AprendizServiceImpl service;

	private final GenerarFormatoInscripcionService generarFormatoInscripcionService;
	
	@Autowired
	MailServiceImpl mailService;


	@GetMapping("/aprendizeditempresa/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Aprendiz> getByIdEmpresa(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(servicer.findByIdEmpresa(id));
		} catch (IOException e) {
			return new ResponseEntity<Aprendiz>(new Aprendiz(), HttpStatus.FORBIDDEN);
		}
	}
/*
	@GetMapping("/aprendizempresa/{idempresa}")
	@ResponseStatus(HttpStatus.OK)
	public List<Aprendiz> getByIdempresa(@PathVariable Long idempresa) {
		return servicer.findByidempresa(idempresa);
	}
*/
	@GetMapping("/asistenciaaprendiz/{idaprendiz}")
	@ResponseStatus(HttpStatus.OK)
	public List<Asistencia> getAsistenciaAprendiz(@PathVariable Long idaprendiz) throws BusinessException {
		return asistenciaService.find(idaprendiz.intValue());
	}
	
	@GetMapping("/evaluaciongrupo/{idgrupo}")
	@ResponseStatus(HttpStatus.OK)
	public List<com.seguridadlimite.models.aprendiz.domain.Aprendiz> getEvaluacionBygrupo(@PathVariable Long idgrupo) {
		return service.findEvaluacionByidgrupo(idgrupo);
	}

	@GetMapping("/aprendiznumerodocumentorempresa/{idempresa}")
	@ResponseStatus(HttpStatus.OK)
	public List<Aprendiz> getByNumerodocumentor(@PathVariable Long idempresa) {
		return servicer.findByEmpresa(idempresa);
	}

	@PostMapping("saveAprendiz")
	@ResponseStatus(HttpStatus.CREATED)
	public com.seguridadlimite.models.aprendiz.domain.Aprendiz create(@RequestBody com.seguridadlimite.models.aprendiz.domain.Aprendiz entity) {
		return servicer.save(entity);
	}

	@DeleteMapping("/deleteAprendiz/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
//		servicer.delete(id);
	}

	@PostMapping(path = "updateEvaluaciongrupo", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public RespuestaWs saveEvaluacion(@RequestBody List<com.seguridadlimite.models.aprendiz.domain.Aprendiz> aprendiz) {
		try {
			service.update(aprendiz);
			return new RespuestaWs();
		} catch (Exception e) {
			return new RespuestaWs(e);
		}
	}
	
	@PostMapping(path = "saveDocumentoevaluacion", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RespuestaWs> saveDocumentotrabajador(@RequestBody Documentoevaluacion entity) throws IOException {
		

		service.saveDocumentoevaluacion(entity);
		return new ResponseEntity<RespuestaWs>(new RespuestaWs(), HttpStatus.OK);

	}

	@GetMapping("/{id}/formatoinscripcion")
	public ReportePojo formatoinscripcionReport(@PathVariable Long id)  {
		String r = generarFormatoInscripcionService.exporterFormatoinscripcionReport(id);
		return new ReportePojo(r);
	}

	@GetMapping("/{id}/perfilingreso")
	public ReportePojo perfilingresoReport(@PathVariable Long id)  {

		String r = generarFormatoInscripcionService.exporterPerfilingresoReport(id);
		return new ReportePojo(r);
	}
	
	@GetMapping("/evaluacionpracticapdf/{id}")
	public ReportePojo evaluacionpracticaReport(@PathVariable Long id) throws JRException, FileNotFoundException {
		String r;
		ReportePojo rep;

		r = generarFormatoInscripcionService.exporterEvaluacionPacticaReport(id);
		rep = new ReportePojo(r);

		return rep;
	}
	
	@GetMapping("/formatoevaluacion/{id}")
	public ReportePojo formatoevaluacionReport(@PathVariable Long id) throws JRException, FileNotFoundException {
		return new ReportePojo(generarFormatoInscripcionService.exporterFormatoevaluacionReport(id));
	}
	
	@GetMapping("/fichacompleta/{id}")
	public ReportePojo fichacompletaReport(@PathVariable Long id) throws JRException, FileNotFoundException {
		return new ReportePojo(generarFormatoInscripcionService.exporterFichaCompletaReport(id));
	}

	@PostMapping("updateIntentos/{idaprendiz}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RespuestaWs> updateIntentos(@PathVariable Long idaprendiz) {
		try {
			if (service.updateIntentos(idaprendiz) == 1) {
				return new ResponseEntity<RespuestaWs>(new RespuestaWs(), HttpStatus.OK);
			} else {
				return new ResponseEntity<RespuestaWs>(new RespuestaWs("Supera el numero de intentos"), HttpStatus.OK);
			}
		} catch (Exception e) {
			mailService.sendException("updateIntentos/" + idaprendiz, e);
			return new ResponseEntity<RespuestaWs>(new RespuestaWs(e), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("reiniciarIntentos/{idaprendiz}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RespuestaWs> reiniciarIntentos(@PathVariable Long idaprendiz) {
		try {
			if (service.reiniciarIntentos(idaprendiz) == 1) {
				return new ResponseEntity<RespuestaWs>(new RespuestaWs(), HttpStatus.OK);
			} else {
				return new ResponseEntity<RespuestaWs>(new RespuestaWs("No encontro registros que actualizar"), HttpStatus.OK);
			}
		} catch (Exception e) {
			mailService.sendException("updateIntentos/" + idaprendiz, e);
			return new ResponseEntity<RespuestaWs>(new RespuestaWs(e), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/aprendizinscripcion/{numerodocumento}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<com.seguridadlimite.models.aprendiz.domain.Aprendiz> getTrabajadorinscripcion(
			@PathVariable String numerodocumento) {
		com.seguridadlimite.models.aprendiz.domain.Aprendiz t;
		
		t = service.findAprendizInscripcion(numerodocumento);
		if (t == null) {
			t = new com.seguridadlimite.models.aprendiz.domain.Aprendiz();
		}
		return new ResponseEntity<com.seguridadlimite.models.aprendiz.domain.Aprendiz>(t, HttpStatus.OK);
	}
}
