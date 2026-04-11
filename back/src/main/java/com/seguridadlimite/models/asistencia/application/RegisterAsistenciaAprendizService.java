package com.seguridadlimite.models.asistencia.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.asistencia.infrastructure.persistence.IAsistenciaDao;
import com.seguridadlimite.models.disenocurricular.application.FindDisenocurricularByIdnivelService;
import com.seguridadlimite.models.disenocurricular.domain.Disenocurricular;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class RegisterAsistenciaAprendizService {

	private final IAsistenciaDao dao;

	private final FindDisenocurricularByIdnivelService disenocurricular;

	public List<Asistencia> find(List<Aprendiz> aprendices) {
		List<Long> aprendizIds = aprendices.stream()
				.map(a -> a.getId() == null ? null : a.getId().longValue())
				.collect(Collectors.toList());

		List<Asistencia> asistencias = dao.findByIdaprendizIn(aprendizIds);

		// Agrupar aprendices por nivel
		Map<Long, List<Aprendiz>> aprendicesPorNivel = aprendices.stream()
				.collect(Collectors.groupingBy(Aprendiz::getIdnivel));

		// Obtener diseños curriculares por nivel una sola vez
		Map<Long, List<Disenocurricular>> disenosPorNivel = aprendicesPorNivel.keySet().stream()
				.collect(Collectors.toMap(
						nivel -> nivel,
						nivel -> disenocurricular.find(nivel)
				));

		aprendizIds.forEach(idaprendiz -> {
			if (idaprendiz == null) {
				return;
			}
			if (asistencias.stream().noneMatch(asistencia -> asistencia.getIdaprendiz().equals(idaprendiz))) {
				Aprendiz aprendiz = aprendices.stream()
						.filter(a -> a.getId() != null && a.getId().longValue() == idaprendiz)
						.findFirst()
						.orElseThrow(() -> new BusinessException("Aprendiz no encontrado"));
				
				generarAsistenciaAprendiz(aprendiz, asistencias, disenosPorNivel.get(aprendiz.getIdnivel()));
			}
		});

		return asistencias;
	}

	@Transactional(readOnly = true)
	public List<Asistencia> find(Long idaprendiz) throws BusinessException {
		return dao.findByIdaprendiz(idaprendiz);
	}

	@Transactional
	private void generarAsistenciaAprendiz(Aprendiz aprendiz,
										   List<Asistencia> asistencias,
										   List<Disenocurricular> disenocurriculars) {
		List<Asistencia> nuevasAsistencias = disenocurriculars.stream()
			.map(disenocurricular -> Asistencia.builder()
				.idaprendiz(aprendiz.getId() == null ? null : aprendiz.getId().longValue())
				.modulo(disenocurricular.getModulo())
				.contexto(disenocurricular.getContexto())
				.unidad(disenocurricular.getUnidad())
				.dia(disenocurricular.getDia())
				.horas(disenocurricular.getHoras())
				.observacion("")
				.build())
			.collect(Collectors.toList());
		
		List<Asistencia> savedAsistencias = StreamSupport
			.stream(dao.saveAll(nuevasAsistencias).spliterator(), false)
			.collect(Collectors.toList());
		
		asistencias.addAll(savedAsistencias);
	}
}
