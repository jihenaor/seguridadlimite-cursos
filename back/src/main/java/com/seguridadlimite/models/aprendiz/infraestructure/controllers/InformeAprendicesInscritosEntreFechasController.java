package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.informeAprendicesInscritosFecha.InformeAprendicesInscritosEntreFecha;
import com.seguridadlimite.models.aprendiz.application.informeAprendicesInscritosFecha.RegistroInformeInscritosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/aprendiz")
@RequiredArgsConstructor
public class InformeAprendicesInscritosEntreFechasController {

	private final InformeAprendicesInscritosEntreFecha informeAprendicesInscritosEntreFecha;

	@GetMapping("/informeaprendicesinscritos/{fechaInicial}/{fechaFinal}/{origen}")
	public List<RegistroInformeInscritosDto> asistenciareport(
			@PathVariable String fechaInicial,
			@PathVariable String fechaFinal,
			@PathVariable String origen) {

		return informeAprendicesInscritosEntreFecha.generarInformeAprendicesInscritosEntreFecha(fechaInicial, fechaFinal, origen);

	}
}
