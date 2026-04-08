package com.seguridadlimite.springboot.backend.apirest.controllers;

import com.seguridadlimite.models.dao.EvaluacionPojo;
import com.seguridadlimite.models.entity.ReportePojo;
import com.seguridadlimite.models.entity.RespuestaWs;
import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.springboot.backend.apirest.services.EvaluacionServiceImpl;
import com.seguridadlimite.springboot.backend.apirest.services.MailServiceImpl;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EvaluacionRestController {
	private final EvaluacionServiceImpl service;

	private final MailServiceImpl mailService;
	
	@GetMapping("/evaluacion/{tipoevaluacion}/{idaprendiz}/{numeroevaluacion}/{idnivel}")
	public List<Evaluacion> index(
            @PathVariable String tipoevaluacion,
			@PathVariable Long idaprendiz, 
			@PathVariable Integer numeroevaluacion,
			@PathVariable Long idnivel) {
		EvaluacionPojo e = service.findAndRegister(
                tipoevaluacion,
                idaprendiz,
                numeroevaluacion,
                idnivel);

        return e.getEvaluacions();
	}

	@GetMapping("/evaluacionteorica/{idaprendiz}")
	public List<Pregunta> evaluacionteorica(
			@PathVariable Long idaprendiz) {
		return service.findEvaluacionTeoricaIdaprendiz(idaprendiz);
	}

	@GetMapping("/evaluacionpracticamovil/{idaprendiz}")
	public ResponseEntity<List<Pregunta>> evaluacionpracticamovil(@PathVariable Long idaprendiz) {
		List<Pregunta> l;
		try {
			l = service.findEvaluacionPracticaMovilIdaprendiz(idaprendiz);
			
			 return new ResponseEntity<>(l, HttpStatus.OK);
		} catch (Exception e) {
			mailService.sendException("evaluacionpracticamovil/" + idaprendiz, e);
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping(path = "saveEvaluacion", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public RespuestaWs create(@RequestBody List<Evaluacion> evaluacions) {
		try {
			service.save(evaluacions);
			return new RespuestaWs();
		} catch (Exception e) {
			return new RespuestaWs(e);
		}
	}
	
	@PostMapping(path = "saveevaluacionpracticamovil/{tipoevaluacion}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public RespuestaWs saveevaluacionpracticamovil(@PathVariable String tipoevaluacion,
												   @RequestBody List<Pregunta> preguntas) throws Exception {

		service.saveevaluacion(preguntas, tipoevaluacion);
		return new RespuestaWs();

	}

	@PostMapping(path = "updateEvaluacionaprendiz", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public RespuestaWs saveEvaluacion(@RequestBody List<Evaluacion> evaluacions) {
		try {
			service.update(evaluacions);
			return new RespuestaWs();
		} catch (Exception e) {
			e.printStackTrace();
			return new RespuestaWs(e);
		}
	}	
	
	@DeleteMapping("/deleteEvaluacion/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@GetMapping("/formatoevaluacionpractica/{idaprendiz}")
	public ReportePojo formatoevaluacionpractica(@PathVariable Long idaprendiz) throws JRException, FileNotFoundException {


		return new ReportePojo( service.exporterFormatoevaluacionpracticaReport(idaprendiz));

	}
}
