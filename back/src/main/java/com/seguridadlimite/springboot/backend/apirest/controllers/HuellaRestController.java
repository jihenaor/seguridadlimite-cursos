package com.seguridadlimite.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seguridadlimite.models.entity.Huella;
import com.seguridadlimite.springboot.backend.apirest.services.HuellaServiceImpl;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class HuellaRestController extends Controller {

	@Autowired
	private HuellaServiceImpl service;
	
	@GetMapping("/findhuella")
	public List<Huella> index() {
		List<Huella> l = service.findAll();
		
		return l;
	}
	
	@PostMapping(path = "savehuella", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Huella> saveHuella(@RequestBody Huella entity) {
		
		try {
			Huella huella = service.save(entity);
			
			return new ResponseEntity<Huella>(huella, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Huella>(entity, HttpStatus.BAD_REQUEST);
		}
		
		
	}
}
