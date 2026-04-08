package com.seguridadlimite.models.quiz.application;

import com.seguridadlimite.models.aprendiz.application.findById.FindAprendizByIdService;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pojo.RespuestaEncuestaSatisfaccion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.pregunta.domain.TipoevaluacionEnum;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsultarEncuestaSatisfaccionClienteService {
    private final IAprendizDao aprendizDao;
	private final IEvaluacionDao dao;
	private final IPreguntaDao preguntaDao;
	private final FindAprendizByIdService consultarAprendizPorId;

	public RespuestaEncuestaSatisfaccion findPreguntasAprendiz(Long idaprendiz) throws BusinessException {
		boolean activacomentarios = false;
		List<Pregunta> preguntas = consultarPreguntas(idaprendiz);

		if (!preguntas.isEmpty()) {
			if (preguntas.get(preguntas.size() - 1).getRespuestacorrecta().equals("S")) {
				activacomentarios = true;
			}
		}

		Aprendiz aprendiz = consultarAprendizPorId.find(idaprendiz);

		return new RespuestaEncuestaSatisfaccion(
				aprendiz.getComentariosencuesta(),
				activacomentarios,
				preguntas);
	}

	private List<Pregunta> consultarPreguntas(
			Long idaprendiz
            ) throws BusinessException {
        String sabeLeerEscribir = aprendizDao.consultarAprendizSabeLeerEscribir(idaprendiz);
		List<Pregunta> preguntas = preguntaDao.findTipoevaluacion(
                TipoevaluacionEnum.ENCUESTA.getEquivalente(),
                sabeLeerEscribir == null ? "N" : sabeLeerEscribir);

		List<Evaluacion> l = getEvaluacions(idaprendiz,
				preguntas);

		for (Pregunta pregunta : preguntas) {
			for (Evaluacion ev : l) {
				if (ev.getIdpregunta().equals(pregunta.getId())) {
					pregunta.setIdevaluacion(ev.getId());
					pregunta.setRespuestacorrecta(ev.getRespuestacorrecta());
					break;
				}
			}
		}

		return preguntas;
	}

	@Transactional
	private List<Evaluacion> getEvaluacions(Long idaprendiz,
											List<Pregunta> preguntas) {
		List<Evaluacion> l = dao.findEvaluacionTipo(
				idaprendiz,
				TipoevaluacionEnum.ENCUESTA.getEquivalente());

		assert l != null;
		if (l.isEmpty()) {
			l = new ArrayList<>();
			for (Pregunta pregunta : preguntas) {
				l.add(getEvaluacion(idaprendiz, pregunta));
			}
			dao.saveAll(l);
		}
		return l;
	}

	private static Evaluacion getEvaluacion(long idaprendiz,
											Pregunta pregunta) {
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setIdaprendiz(idaprendiz);
		evaluacion.setIdpregunta(pregunta.getId());
		evaluacion.setRespuestacorrecta("N");
		evaluacion.setNumero(0);
		evaluacion.setCreateAt(new Date());
		evaluacion.setUpdateAt(new Date());
		return evaluacion;
	}
}
