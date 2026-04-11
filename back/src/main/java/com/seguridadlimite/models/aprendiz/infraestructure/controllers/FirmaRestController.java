package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.entity.FirmaAprendiz;
import com.seguridadlimite.models.trabajador.application.TrabajadorService;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import com.seguridadlimite.springboot.backend.apirest.util.EncodeFileToBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/aprendiz")
@ResponseBody
@RequiredArgsConstructor
public class FirmaRestController extends Controller {

	private final TrabajadorService service;

	private final EncodeFileToBase64 encodeFile;

	@GetMapping(path = "/{idaprendiz}/firma", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<FirmaAprendiz> getFoto(
			@PathVariable Long idaprendiz) {


		Optional<String> base64 = encodeFile.encode("S", idaprendiz.toString());

		return new ResponseEntity<>(
                new FirmaAprendiz(idaprendiz, base64.isPresent() ? base64.get() : ""),
                HttpStatus.OK);

	}
}
