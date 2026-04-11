package com.seguridadlimite.models.trabajador.infraestructure.controller;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.entity.Fototrabajador;
import com.seguridadlimite.springboot.backend.apirest.controllers.Controller;
import com.seguridadlimite.springboot.backend.apirest.util.EncodeFileToBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/aprendiz")
@ResponseBody
@RequiredArgsConstructor
public class TrabajadorFotoGetController extends Controller {

	private final EncodeFileToBase64 encodeFile;

	@GetMapping(path = "/foto/{trabajadorid}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Fototrabajador> getFoto1(
			@PathVariable Long trabajadorid) {
		Fototrabajador fototrabajador = new Fototrabajador();
		Optional<String> base64 = encodeFile.encode("F", trabajadorid.toString());
		if (base64.isPresent()) {
			fototrabajador.setBase64(base64.get());
			return new ResponseEntity<Fototrabajador>(fototrabajador, HttpStatus.OK);
		} else {
			throw new RuntimeException("No existe foto");
		}
	}
}
