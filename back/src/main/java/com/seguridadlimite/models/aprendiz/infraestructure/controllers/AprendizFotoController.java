package com.seguridadlimite.models.aprendiz.infraestructure.controllers;

import com.seguridadlimite.models.aprendiz.application.extraerFoto.ExtraerFotoTrabajadorService;
import com.seguridadlimite.models.aprendiz.application.extraerfirma.IExtraerFirmaService;
import com.seguridadlimite.models.aprendiz.domain.FirmaDTO;
import com.seguridadlimite.models.aprendiz.domain.FotoDTO;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aprendiz")
@AllArgsConstructor
public class AprendizFotoController {

	private final ExtraerFotoTrabajadorService service;

	private final IExtraerFirmaService firmaService;

	@GetMapping("/{idtrabajador}/foto")
	public ResponseEntity<FotoDTO> findAprendicesInscritos(@PathVariable Long idtrabajador) throws BusinessException {
		String fotoBase64 = service.buscar(idtrabajador);
		FotoDTO foto = new FotoDTO(
					idtrabajador,
					fotoBase64,
					"jpg"
		);
		
		return ResponseEntity.ok(foto);
	}

	@GetMapping("/{idaprendiz}/firma")
	public ResponseEntity<FirmaDTO> extraerFirma(@PathVariable Long idaprendiz) throws BusinessException {
		String firmaBase64 = firmaService.extraerFirma(idaprendiz);
		FirmaDTO firma = new FirmaDTO(
				idaprendiz,
				firmaBase64,
				"png"
		);
		
		return ResponseEntity.ok(firma);
	}
}

