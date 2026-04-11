package com.seguridadlimite.springboot.backend.apirest.services;

import com.seguridadlimite.iservices.INivelInscripcionService;
import com.seguridadlimite.models.disenocurricular.application.FindDisenocurricularByIdnivelService;
import com.seguridadlimite.models.disenocurricular.domain.Disenocurricular;
import com.seguridadlimite.models.nivel.domain.Nivel;
import com.seguridadlimite.models.nivel.domain.dto.DiaDto;
import com.seguridadlimite.models.nivel.infraestructure.INivelDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class NivelInscripcionServiceImpl implements INivelInscripcionService {
	private final INivelDao dao;
	private final FindDisenocurricularByIdnivelService findDisenocurricularByIdnivelService;

	@Override
	@Transactional(readOnly = true)
	public List<Nivel> findActivos() {
		List<Nivel> niveles = dao.findByEstado("A");

		for (Nivel nivel : niveles) {
			List<DiaDto> diasDto = new ArrayList<>();
			Map<Integer, DiaDto> diasMap = new HashMap<>();

			List<Disenocurricular> disenocurriculars = findDisenocurricularByIdnivelService.find(nivel.getId());
			for (Disenocurricular disenocurricular : disenocurriculars) {
				short dia = disenocurricular.getDia();
				
				// Si el día ya existe, actualizar contexto y unidad (tomar el último)
				// Si no existe, crear nuevo DiaDto
				DiaDto diaDto = diasMap.get(dia);
				if (diaDto == null) {
					diaDto = new DiaDto(
						dia,
						"", // fecha se puede calcular o dejar vacía por ahora
						false, // seleccionado por defecto false
						disenocurricular.getContexto(),
						disenocurricular.getUnidad()
					);
					diasMap.put((int) dia, diaDto);
				} else {
					// Actualizar contexto y unidad con los valores más recientes
					diaDto.setContexto(disenocurricular.getContexto());
					diaDto.setUnidad(disenocurricular.getUnidad());
				}
			}

			// Convertir el map a lista
			diasDto.addAll(diasMap.values());
			
			nivel.setDiasdiseno(diasDto);
		}
		return niveles;
	}
}
