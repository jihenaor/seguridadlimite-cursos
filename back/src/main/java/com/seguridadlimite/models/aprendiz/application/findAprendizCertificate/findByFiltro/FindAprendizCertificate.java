package com.seguridadlimite.models.aprendiz.application.findAprendizCertificate.findByFiltro;

import com.seguridadlimite.models.aprendiz.application.findByFiltro.AprendizFindByFilter;
import com.seguridadlimite.models.aprendiz.domain.AprendizEvaluacionDTO;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAprendizCertificate {

	private final IAprendizDao dao;

	private final AprendizFindByFilter aprendizFindByFilter;

	public List<AprendizEvaluacionDTO> findByFiltro(String filtro) {
		return aprendizFindByFilter.findByFiltro(filtro);

	}

	public List<AprendizEvaluacionDTO> findByNit(
			String filtro,
			String fechaInicial,
			String fechaFinal) {
		return aprendizFindByFilter.findByNit(filtro);
	}
}
