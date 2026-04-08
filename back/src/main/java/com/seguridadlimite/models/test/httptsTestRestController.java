package com.seguridadlimite.models.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seguridadlimite.models.entity.Test;

@RestController
@RequestMapping("/api")
public class httptsTestRestController {

	@GetMapping("/httpstest")
	public Test index() {
		Test t = new Test("200", "https");
		
		return t;
	}
}
