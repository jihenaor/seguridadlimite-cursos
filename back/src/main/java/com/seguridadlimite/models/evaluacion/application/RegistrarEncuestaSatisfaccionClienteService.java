package com.seguridadlimite.models.evaluacion.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.application.findById.FindAprendizByIdService;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrarEncuestaSatisfaccionClienteService {

	private final IEvaluacionDao dao;

	private final IAprendizDao aprendizDao;

	private final FindAprendizByIdService aprendizPorId;

	@Transactional
	public void saveEncuesta(List<Pregunta> entity, Long idaprendiz) throws Exception {
		Aprendiz aprendiz;

		if (idaprendiz == null ) {
			throw new Exception("No se ha seleccionado el aprendiz");
		}

		aprendiz = aprendizPorId.find(idaprendiz.intValue());

		for (Pregunta p : entity) {
			if (p.getIdevaluacion() == null) {
				throw new Exception(String.format("Error una pregunta %d - %s sin id evaluacion",
						p.getId(),
						p.getPregunta()));
			} else {
				actualizarEvaluacion(p);
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = dateFormat.format(new Date());

		aprendizDao.updateFechaEncuesta(fechaActual,
										aprendiz.getId());
	}

	private void actualizarEvaluacion(Pregunta p) {
		Evaluacion e = dao.findById(p.getIdevaluacion()).orElse(null);

		if (e != null) {
			e.setRespuestacorrecta(p.getRespuestacorrecta());
			e.setNumerorespuesta(p.getNumerorespuesta());

			dao.save(e);
		}
	}
}
