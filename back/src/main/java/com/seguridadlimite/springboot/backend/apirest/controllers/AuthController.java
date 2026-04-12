package com.seguridadlimite.springboot.backend.apirest.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.entity.Empresa;
import com.seguridadlimite.models.personal.application.PersonalService;
import com.seguridadlimite.models.personal.dominio.Personal;
import com.seguridadlimite.models.trabajador.application.TrabajadorFindByDocumentoCu;
import com.seguridadlimite.models.trabajador.application.TrabajadorService;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.dto.AuthenticationRequest;
import com.seguridadlimite.springboot.backend.apirest.dto.AuthenticationResponse;
import com.seguridadlimite.springboot.backend.apirest.services.EmpresaServiceImpl;
import com.seguridadlimite.springboot.backend.apirest.services.MailServiceImpl;
import com.seguridadlimite.security.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final PersonalService personalService;
	
	private final EmpresaServiceImpl empresaService;
	
	private final TrabajadorService trabajadorService;

	private final TrabajadorFindByDocumentoCu trabajadorFindByDocumentoCu;

	@Autowired
	MailServiceImpl mailService;

	private final JwtService jwtService;


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request) {
		// Empresa: el NIT suele ser numérico; debe resolverse ANTES que personal (antes se usaba
		// UtilidadNumero.esNumero(username) en la rama personal y el login E caía en sl_personal → 401).
		if ("E".equals(request.getPerfil())) {
			Empresa e = empresaService.findByNumerodocumento(request.getUsername());
			if (e != null) {
				String jwt = jwtService.generateToken(request.getUsername());
				return new ResponseEntity<>(new AuthenticationResponse(jwt, e), HttpStatus.OK);
			}
			log.warn("Login empresa 401 — NIT/username no encontrado (sin registrar contraseña en log)");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		// Administración / personal (perfil A, null u otro distinto de E)
		Personal p = personalService.authenticate(request.getUsername(), request.getPassword());
		if (p != null) {
			String jwt = jwtService.generateToken(request.getUsername());
			return new ResponseEntity<>(new AuthenticationResponse(jwt, p), HttpStatus.OK);
		}
		log.warn("Login personal 401 — usuario o contraseña incorrectos (perfil={})",
				request.getPerfil() == null ? "null" : request.getPerfil());
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/updatepassword")
    public ResponseEntity<AuthenticationResponse> updatepassword(@RequestBody AuthenticationRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !StringUtils.hasText(auth.getName())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try {
			Personal p = personalService.updatePasswordIfSubjectMatches(
					request.getId(),
					auth.getName(),
					request.getPassword(),
					request.getCpassword());
			String jwt = jwtService.generateToken(auth.getName());
			return new ResponseEntity<>(new AuthenticationResponse(jwt, p), HttpStatus.OK);
		} catch (SecurityException e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }

    @PostMapping("/authenticateempresa")
    public ResponseEntity<AuthenticationResponse> createTokenempresa(@RequestBody AuthenticationRequest request) {
    	Empresa p = empresaService.findByNumerodocumento(request.getUsername());

    	if (p == null) {
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
        try {
            String jwt = jwtService.generateToken(request.getUsername());
            return new ResponseEntity<>(new AuthenticationResponse(jwt, p), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/authenticatetrabajador")
    public ResponseEntity<AuthenticationResponse> createTokentrabajador(@RequestBody AuthenticationRequest request) {
        try {
	    	Trabajador p = trabajadorFindByDocumentoCu.findByNumerodocumento(request.getUsername());

	    	if (p == null) {
	    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    	}

            String jwt = jwtService.generateToken(request.getUsername());
            return new ResponseEntity<>(new AuthenticationResponse(jwt, p), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthenticationResponse(e), HttpStatus.FORBIDDEN);
        }
    }
}
