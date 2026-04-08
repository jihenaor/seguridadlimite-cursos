package com.seguridadlimite.models.quiz.infraestructure.controller;

import com.seguridadlimite.models.aprendiz.application.ActualizarComentariosEncuesta;
import com.seguridadlimite.models.entity.RespuestaWs;
import com.seguridadlimite.models.evaluacion.application.RegistrarEncuestaSatisfaccionClienteService;
import com.seguridadlimite.models.pojo.RespuestaEncuestaSatisfaccion;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluacion")
@AllArgsConstructor
public class RegistrarEncuestaSatisfaccionClienteController {

	private final RegistrarEncuestaSatisfaccionClienteService service;
	private final ActualizarComentariosEncuesta actualizarComentariosEncuesta;

	@PostMapping(path = "/{idaprendiz}/encuestasatisfaccioncliente", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<RespuestaWs> saveencuestasatisfaccioncliente(
			@PathVariable Long idaprendiz,
			@RequestBody RespuestaEncuestaSatisfaccion respuestaEncuestaSatisfaccion) throws Exception {


		actualizarComentariosEncuesta.update(idaprendiz, respuestaEncuestaSatisfaccion.getComentariosencuesta());
		service.saveEncuesta(respuestaEncuestaSatisfaccion.getPreguntas(), idaprendiz);
		return new ResponseEntity(new RespuestaWs(), HttpStatus.OK);
	}
}
