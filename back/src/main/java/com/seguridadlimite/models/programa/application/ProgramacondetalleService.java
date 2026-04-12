package com.seguridadlimite.models.programa.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.programa.infraestructure.IProgramaDao;
import com.seguridadlimite.models.programa.model.Programa;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramacondetalleService {

	private final IProgramaDao dao;

	@Transactional(readOnly = true)
	public List<Programa> findAll() {
		return (List<Programa>) dao.findAll();
	}

	/**
	 * Programas expuestos al asistente de preinscripción (lista ligera id + nombre).
	 * La entidad {@link Programa} no modela estado "activo"; se devuelven todos los registros de catálogo.
	 */
	@Transactional(readOnly = true)
	public List<ProgramaDto> findAllCursosActivos() {
		List<Programa> programas = (List<Programa>) dao.findAll();
		return programas.stream()
				.map(p -> new ProgramaDto(p.getId(), p.getNombre()))
				.toList();
	}

	@Transactional(readOnly = true)
	public Programa findById(Long id) {
		return dao.findById(id).orElse(null);
	}

}
