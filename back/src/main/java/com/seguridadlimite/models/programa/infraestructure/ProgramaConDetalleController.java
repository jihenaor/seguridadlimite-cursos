package com.seguridadlimite.models.programa.infraestructure;

import com.seguridadlimite.models.programa.application.ProgramaDto;
import com.seguridadlimite.models.programa.application.ProgramacondetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/programas")
public class ProgramaConDetalleController {

	@Autowired
	private ProgramacondetalleService servicecondetalle;

	@GetMapping("/activoscondetalle")
	public List<ProgramaDto> indexcondetalle() {
		return servicecondetalle.findAllCursosActivos();
	}


}
