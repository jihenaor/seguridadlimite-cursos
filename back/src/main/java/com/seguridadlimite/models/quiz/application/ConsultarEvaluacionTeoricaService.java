package com.seguridadlimite.models.quiz.application;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import com.seguridadlimite.models.pregunta.application.inicializarimagenes.InicializarImagenes;
import com.seguridadlimite.models.pregunta.domain.TipoevaluacionEnum;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.*;
import com.seguridadlimite.models.entity.*;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.seguridadlimite.models.pregunta.domain.TipoevaluacionEnum.INGRESO;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultarEvaluacionTeoricaService {

	private final IEvaluacionDao dao;

	private final IPreguntaDao preguntaDao;

	private final IAprendizDao aprendizDao;

	private final InicializarImagenes inicializarImagenes;


	/**
	 * Transacción: mantiene la sesión abierta para lazy ({@code Pregunta.respuestas}) y permite
	 * escrituras en {@code getEvaluacions} (p. ej. {@code saveAll}). No usar {@code readOnly=true}
	 * aquí: marcaría la conexión JDBC de solo lectura y fallarían los inserts en {@code sl_evaluaciones}.
	 */
	@Transactional
	public List<Pregunta> findPreguntasAprendiz(int idaprendiz,
												String tipoevaluacion) throws BusinessException {
		Aprendiz aprendiz = aprendizDao.findById(idaprendiz)
				.orElseThrow(() -> new NoSuchElementException("No se encontró el aprendiz con el ID proporcionado"));

		if (aprendiz.getIdenfasis() == null) {
			throw new BusinessException("El aprendiz debe tener énfasis asignado para cargar la evaluación.");
		}
		List<Long> idsEnfasisEvaluacion = idsEnfasisParaEvaluacion(aprendiz.getIdenfasis().longValue());

		int numeroevaluacion = 0;

		if (tipoevaluacion.equals(TipoevaluacionEnum.TEORICO.getEquivalente())) {
			numeroevaluacion = aprendiz.getEteorica1() == 0 ? 1 : 2;
		}

        List<Pregunta> preguntas = consultarPreguntas(idaprendiz,
					numeroevaluacion,
					aprendiz.getIdnivel() == null ? null : aprendiz.getIdnivel(),
					idsEnfasisEvaluacion,
					tipoevaluacion,
                    aprendiz.getSabeleerescribir() == null ? "S" : aprendiz.getSabeleerescribir());

		if (TipoevaluacionEnum.TEORICO.getEquivalente().equals(tipoevaluacion)) {
			if (!existeTipoEvaluacionE(preguntas)) {
				throw new BusinessException("No existe una pregunta Teorica para:" 
				+ " Nivel: (" + aprendiz.getIdnivel() + ") " + aprendiz.getNivel().getNombre() 
				+ " Enfasis: (" + aprendiz.getIdenfasis() + ") " + aprendiz.getEnfasis().getNombre()
				+ " Numero de evaluacion: " + numeroevaluacion);
			}
		}
		return preguntas;
	}

	/**
	 * Ids de énfasis aplicables a la evaluación: siempre incluye el genérico {@code 0} y el del aprendiz
	 * (sin duplicar si el aprendiz ya tiene énfasis 0). Se puede ampliar la lista desde reglas de negocio.
	 */
	private static List<Long> idsEnfasisParaEvaluacion(long idenfasisAprendiz) {
		LinkedHashSet<Long> ids = new LinkedHashSet<>();
		ids.add(0L);
		ids.add(idenfasisAprendiz);
		return new ArrayList<>(ids);
	}

	private List<Pregunta> consultarPreguntas(
			int idaprendiz,
		 	int numeroevaluacion,
			Integer idnivel,
			List<Long> idsEnfasis,
			String tipoevaluacion,
            String sabeleerescribir) throws BusinessException {
		List<Pregunta> preguntas;
		String caminoConsulta;

		if (tipoevaluacion.equals(TipoevaluacionEnum.TEORICO.getEquivalente())) {
			if (idnivel == null) {
				throw new BusinessException("El aprendiz no tiene nivel asignado; no se pueden cargar preguntas teóricas.");
			}
			caminoConsulta = String.format(
					"TEORICO.findPreguntasEvaluacionTeorica(idNivel=%d, idsEnfasis=%s, diflectura=%s)",
					idnivel.longValue(),
					idsEnfasis,
					sabeleerescribir);
			preguntas = preguntaDao.findPreguntasEvaluacionTeorica(
                    idnivel.longValue(),
                    idsEnfasis,
                    sabeleerescribir);
		} else if (tipoevaluacion.equals(INGRESO.getEquivalente())) {
			if (numeroevaluacion == 0) {
				caminoConsulta = String.format(
						"INGRESO.findTipoevaluacionIngresoPorEnfasis(tipo=%s, diflectura=%s, idsEnfasis=%s)",
						INGRESO.getEquivalente(),
						sabeleerescribir,
						idsEnfasis);
				preguntas = preguntaDao.findTipoevaluacionIngresoPorEnfasis(
						INGRESO.getEquivalente(),
						sabeleerescribir,
						idsEnfasis);
			} else {
				caminoConsulta = String.format(
						"INGRESO.findTipoevaluacion(tipo=%s, diflectura=%s, numeroEvaluacion=%d)",
						INGRESO.getEquivalente(),
						sabeleerescribir,
						numeroevaluacion);
				preguntas = preguntaDao.findTipoevaluacion(INGRESO.getEquivalente(), sabeleerescribir);
			}
		} else {
			throw new BusinessException("El tipo de evaluacion " + tipoevaluacion + " no es valido");
		}

		if (preguntas == null || preguntas.isEmpty()) {
			log.warn(
					"Evaluación sin preguntas — idAprendiz={} camino={} tipoevaluacion={} numeroEvaluacion={} "
							+ "idNivel={} idsEnfasis={} diflectura={}",
					idaprendiz,
					caminoConsulta,
					tipoevaluacion,
					numeroevaluacion,
					idnivel,
					idsEnfasis,
					sabeleerescribir);
			throw new BusinessException(
					"No se encontraron preguntas para esta evaluación. Camino de consulta: "
							+ caminoConsulta
							+ ". Compruebe que existan preguntas en base de datos para el nivel, énfasis y tipo de lectura indicados.");
		}

		inicializarImagenes.inicializarImagenes(preguntas);

		List<Evaluacion> l = getEvaluacions(idaprendiz,
				numeroevaluacion,
				preguntas,
				tipoevaluacion);

		validarOpcionesRespuesta(preguntas);
		for (Pregunta pregunta : preguntas) {
			for (Evaluacion ev : l) {
				if (ev.getIdpregunta().equals(pregunta.getId())) {
					pregunta.setIdevaluacion(ev.getId());
					break;
				}
			}
		}

		return preguntas;
	}

	/*
		1: Única respuesta
		2: Cumple / no cumple
		3: Verdadero/Falso
		4: Obligaciones
		5: Medidas preventivas
	 */
	private void validarOpcionesRespuesta(List<Pregunta> preguntas) {
		for (Pregunta pregunta : preguntas) {
			switch (pregunta.getType()) {
				case 3:
					asignarOpcionesFalsoVerdadero(pregunta);
					break;
			}
		}
	}

	private void asignarOpcionesFalsoVerdadero(Pregunta pregunta) {
		List<Respuesta> respuestas = Arrays.asList(
				new Respuesta(1, "Falso", pregunta.getId()),
				new Respuesta(2, "Verdadero", pregunta.getId())
		);

		pregunta.setRespuestas(respuestas);
	}

	/** Participa en la transacción de {@link #findPreguntasAprendiz} (anotación en método privado no aplica AOP). */
	private List<Evaluacion> getEvaluacions(int idaprendiz,
											int numeroevaluacion,
											List<Pregunta> preguntas,
											String tipoevaluacion) {
		List<Evaluacion> l = null;

		if (tipoevaluacion.equals(TipoevaluacionEnum.TEORICO.getEquivalente()))	{
			l = dao.findEvaluacionTeoricaAprendiz(idaprendiz, numeroevaluacion);
		} else if (tipoevaluacion.equals(INGRESO.getEquivalente())) {
			l = dao.findEvaluacionTipo(idaprendiz, "I");
		}

		assert l != null;
		if (l.isEmpty()) {
			l = new ArrayList<>();
			for (Pregunta pregunta : preguntas) {
				l.add(getEvaluacion(idaprendiz, numeroevaluacion, pregunta));
			}


			dao.saveAll(l);
		}
		return l;
	}

	private static Evaluacion getEvaluacion(int idaprendiz, int numeroevaluacion, Pregunta pregunta) {
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setIdaprendiz(idaprendiz);
		evaluacion.setIdpregunta(pregunta.getId());
		evaluacion.setRespuestacorrecta("N");
		evaluacion.setNumero(numeroevaluacion);
		evaluacion.setCreateAt(new Date());
		evaluacion.setUpdateAt(new Date());
		return evaluacion;
	}

	public boolean existeTipoEvaluacionE(List<Pregunta> listaPreguntas) {
		return listaPreguntas.stream()
				.anyMatch(pregunta -> "E".equals(pregunta.getGrupo().getTipoevaluacion()));
	}


}
