package com.seguridadlimite.springboot.backend.apirest.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.entity.Perfil;
import com.seguridadlimite.models.entity.PerfilRequest;
import com.seguridadlimite.models.personal.dominio.Personal;
import com.seguridadlimite.models.aprendiz.application.AprendizServicerImpl;
import com.seguridadlimite.models.personal.application.PersonalService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthRestController {

	private final AprendizServicerImpl serviceAprendiz;

	private final PersonalService servicePersonal;

	@PostMapping("auth")
	@ResponseStatus(HttpStatus.CREATED)
	public Perfil create(@RequestBody PerfilRequest perfilRequest) {
		Aprendiz aprendiz;
		Personal personal;
		Perfil perfil = new Perfil();
/*
		aprendiz = serviceAprendiz.findByNumerodocumento(perfilRequest.getNumerodocumento());

		if (aprendiz == null) {
			personal = servicePersonal.findByNumerodocumento(perfilRequest.getNumerodocumento());

			if (personal != null) {
				perfil.setPerfil("I");
				perfil.setPersonal(personal);
			}
		} else {
			perfil.setPerfil("A");
			perfil.setAprendiz(aprendiz);
		}
*/
		return perfil;
	}


}
