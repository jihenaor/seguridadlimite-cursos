package com.seguridadlimite.models.quiz.application;

import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import com.seguridadlimite.models.pregunta.application.inicializarimagenes.InicializarImagenes;
import com.seguridadlimite.models.pregunta.domain.TipoevaluacionEnum;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
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
public class ConsultarEvaluacionTeoricaService {

	@Autowired
	private IEvaluacionDao dao;

	@Autowired
	private IPreguntaDao preguntaDao;

	@Autowired
	private IAprendizDao aprendizDao;

	@Autowired
	private InicializarImagenes inicializarImagenes;


	public List<Pregunta> findPreguntasAprendiz(Long idaprendiz,
												String tipoevaluacion) throws BusinessException {
		Aprendiz aprendiz = aprendizDao.findById(idaprendiz)
				.orElseThrow(() -> new NoSuchElementException("No se encontró el aprendiz con el ID proporcionado"));

		int numeroevaluacion = 0;

		if (tipoevaluacion.equals(TipoevaluacionEnum.TEORICO.getEquivalente())) {
			numeroevaluacion = aprendiz.getEteorica1() == 0 ? 1 : 2;
		}

        List<Pregunta> preguntas = consultarPreguntas(idaprendiz,
					numeroevaluacion,
					aprendiz.getIdnivel(),
					aprendiz.getIdenfasis(),
					tipoevaluacion,
                    aprendiz.getSabeleerescribir() == null ? "S" : aprendiz.getSabeleerescribir());

		if (TipoevaluacionEnum.TEORICO.getEquivalente().equals(tipoevaluacion)) {
			if (!existeTipoEvaluacionE(preguntas)) {
				throw new BusinessException("No existe una pregunta de tipo énfasis en la lista de preguntas para el sector laboral (nivel): " + aprendiz.getEnfasis().getNombre());
			}
		}
		return preguntas;
	}

	private List<Pregunta> consultarPreguntas(
			Long idaprendiz,
		 	Integer numeroevaluacion,
			Long idnivel,
			Long idenfasis,
			String tipoevaluacion,
            String sabeleerescribir) throws BusinessException {
		List<Pregunta> preguntas;
        System.out.println(sabeleerescribir);
		if (tipoevaluacion.equals(TipoevaluacionEnum.TEORICO.getEquivalente())) {
			preguntas = preguntaDao.findPreguntasEvaluacionTeorica(
                    idnivel,
                    idenfasis,
                    sabeleerescribir);
		} else if (tipoevaluacion.equals(INGRESO.getEquivalente())) {
			preguntas = preguntaDao.findTipoevaluacion(
                    "I",
                    sabeleerescribir);
		} else {
			throw new BusinessException("El tipo de evaluacion " + tipoevaluacion + " no es valido");
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

	@Transactional
	private List<Evaluacion> getEvaluacions(Long idaprendiz,
											Integer numeroevaluacion,
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

	private static Evaluacion getEvaluacion(Long idaprendiz, Integer numeroevaluacion, Pregunta pregunta) {
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
