package com.seguridadlimite.models.evaluacion.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.evaluacion.dominio.EvaluacionDTO;
import com.seguridadlimite.models.evaluacion.dominio.EvaluacionMapper;
import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;
import com.seguridadlimite.models.grupopregunta.domain.GrupopreguntaDTO;
import com.seguridadlimite.models.grupopregunta.domain.GrupopreguntaMapper;
import com.seguridadlimite.models.grupopregunta.infraestructure.IGrupopreguntaDao;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import com.seguridadlimite.shared.domain.Constants;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindEvaluacionPracticaServiceImpl {

	private final IGrupopreguntaDao grupoDao;
	private final IPreguntaDao preguntaDao;
	private final EvaluacionService evaluacionService;
	private final GrupopreguntaMapper grupopreguntaMapper;
	private final EvaluacionMapper evaluacionMapper;


	private final  IEvaluacionDao dao;

	private final IAprendizDao aprendizDao;

	@Transactional
	public List<GrupopreguntaDTO> findEvaluacionPracticaIdaprendiz(Long idaprendiz) {
		List<Grupopregunta> grupopreguntas = Optional.ofNullable(grupoDao.findEvaluacionAprendiz(Constants.EVALUACION_PRACTICA))
				.filter(list -> !list.isEmpty())
				.orElseThrow(() -> new BusinessException("No se encontraron los grupos de preguntas"));

		List<EvaluacionDTO> evaluaciones = evaluacionService.findByAprendiz(idaprendiz, Constants.EVALUACION_PRACTICA);

		if (evaluaciones.isEmpty()) {
			evaluaciones = evaluacionService.saveAll(
				asignarPreguntasPreguntasAlAprendiz(idaprendiz, Constants.EVALUACION_PRACTICA)
			);
		}

		List<EvaluacionDTO> finalEvaluaciones = evaluaciones;

		finalEvaluaciones.forEach(evaluacionDTO -> {
			System.out.println(evaluacionDTO.getPregunta().getIdgrupo());
		});

		grupopreguntas.forEach(grupopregunta -> {
			List<EvaluacionDTO> evaluacionesFilter = finalEvaluaciones.stream()
					.filter(e -> Objects.equals(e.getPregunta().getIdgrupo(), grupopregunta.getId()))
					.toList();

			grupopregunta.setEvaluacions(evaluacionesFilter.stream()
					.map(evaluacionMapper::toEntity)
					.collect(Collectors.toList()));
		});

		return grupopreguntas.stream()
				.map(grupopreguntaMapper::toDto)
				.collect(Collectors.toList());
	}

	private List<Evaluacion> asignarPreguntasPreguntasAlAprendiz(
            Long idaprendiz,
            String tipoEvaluacion) {
        String sabeLeerEscribir = aprendizDao.consultarAprendizSabeLeerEscribir(AprendizId.toInteger(idaprendiz));
		List<Pregunta> preguntas = preguntaDao.findTipoevaluacion(
                tipoEvaluacion,
                sabeLeerEscribir);

		if (preguntas == null) {
			throw new BusinessException("En asignar preguntas del aprendiz " + idaprendiz +
					". No hay preguntas de tipo " + tipoEvaluacion);
		}

		return preguntas.stream()
				.map(pregunta -> getEvaluacion(idaprendiz, pregunta))
				.collect(Collectors.toList());
	}

	private Evaluacion getEvaluacion(Long idaprendiz, Pregunta pregunta) {
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setIdaprendiz(idaprendiz);
		evaluacion.setIdpregunta(pregunta.getId());
		evaluacion.setPregunta(pregunta);
		evaluacion.setRespuestacorrecta("X");
		evaluacion.setNumero(0);
		evaluacion.setCreateAt(new Date());
		evaluacion.setUpdateAt(new Date());
		return evaluacion;
	}



	/// //

	@Transactional
	public List<Grupopregunta> findEvaluacionPracticaIdaprendiz2(Long idaprendiz) throws BusinessException {

		Aprendiz aprendiz = aprendizDao.findById(AprendizId.toInteger(idaprendiz)).orElseThrow();

		List<Grupopregunta> gs = grupoDao.findEvaluacionAprendiz("P");

		if (gs.isEmpty()) {
			throw new BusinessException("No se encontraron preguntas");
		}

		for (Grupopregunta grupopregunta : gs) {
			procesarPreguntas(idaprendiz, aprendiz, grupopregunta);
		}

		return gs;
	}

	private void procesarPreguntas(Long idaprendiz, Aprendiz aprendiz, Grupopregunta g) throws BusinessException {
		List<Evaluacion> l;
		List<Pregunta> preguntas;

		preguntas = preguntaDao.findByIdgrupo(g.getId());

		if (preguntas == null) {
			throw new BusinessException("No ha preguntas para el nivel " + aprendiz.getIdnivel());
		}
/*
		l = dao.findEvaluacionIdgrupoAprendiz(g.getId(), idaprendiz);

		if (l.isEmpty()) {
			l = new ArrayList<>();
			for (Pregunta pregunta : preguntas) {
				creevaluacion(idaprendiz, l, pregunta);
			}
			dao.saveAll(l);
		}
		l.forEach(evaluacion -> evaluacion.getPregunta().setGrupo(null));
		l.forEach(evaluacion -> evaluacion.getPregunta().setNivel(null));
		g.setEvaluacions(l);

 */
	}

	private static void creevaluacion(Long idaprendiz,
									  List<Evaluacion> l,
									  Pregunta pregunta) {
		Evaluacion evaluacion = new Evaluacion();

		evaluacion.setIdaprendiz(idaprendiz);
		evaluacion.setIdpregunta(pregunta.getId());
		evaluacion.setPregunta(pregunta);
		evaluacion.setRespuestacorrecta("X");
		evaluacion.setNumero(0);

		evaluacion.setCreateAt(new Date());
		evaluacion.setUpdateAt(new Date());

		l.add(evaluacion);
	}
}
