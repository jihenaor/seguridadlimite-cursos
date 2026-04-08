package com.seguridadlimite.models.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seguridadlimite.models.entity.Test;

@RestController
@RequestMapping("/api")
public class TestRestController {

	@GetMapping("/test")
	public ResponseEntity<Test> index() {
		return ResponseEntity.ok(new Test("200", "OK"));
	}
}
