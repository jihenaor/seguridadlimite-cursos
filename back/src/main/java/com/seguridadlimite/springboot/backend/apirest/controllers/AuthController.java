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
import com.seguridadlimite.springboot.backend.apirest.util.UtilidadNumero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	private final PersonalService personalService;
	
	private final EmpresaServiceImpl empresaService;
	
	private final TrabajadorService trabajadorService;

	private final TrabajadorFindByDocumentoCu trabajadorFindByDocumentoCu;

	@Autowired
	MailServiceImpl mailService;

	private final JwtService jwtService;


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request) {

		if (request.getPerfil() == null || UtilidadNumero.esNumero(request.getUsername()) || request.getPerfil().equals("A")) {
			Personal p = personalService.findByLogin(request.getUsername(), request.getPassword());

			if (p != null) {
				String jwt = jwtService.generateToken(request.getUsername());
				return new ResponseEntity<>(new AuthenticationResponse(jwt, p), HttpStatus.OK);
			}
		}
		else {
			if (request.getPerfil().equals("E")) {
				Empresa e = empresaService.findByNumerodocumento(request.getUsername());

				if (e != null) {
					String jwt = jwtService.generateToken(request.getUsername());
					return new ResponseEntity<>(new AuthenticationResponse(jwt, e), HttpStatus.OK);
				}
			}
		}

    	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/updatepassword")
    public ResponseEntity<AuthenticationResponse> updatepassword(@RequestBody AuthenticationRequest request) {
    	Personal p = personalService.findById(request.getId());

    	if (p == null) {
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
        try {
        	p.setPassword(request.getPassword());
        	personalService.save(p);
			String jwt = jwtService.generateToken(request.getUsername());
        	return new ResponseEntity<>(new AuthenticationResponse(jwt, p), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
