package com.seguridadlimite.springboot.backend.apirest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Detener {

    @RequestMapping("/detener-app")
    public String detenerApp() {
        System.exit(0);
        return "La aplicación ha sido detenida";
    }
}
