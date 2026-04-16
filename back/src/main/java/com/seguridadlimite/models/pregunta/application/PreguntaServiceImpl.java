package com.seguridadlimite.models.pregunta.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.dao.IRespuestaDao;
import com.seguridadlimite.models.entity.Respuesta;
import com.seguridadlimite.models.pregunta.application.inicializarimagenes.InicializarImagenes;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PreguntaServiceImpl {

	private final IPreguntaDao dao;

	private final IEvaluacionDao evaluacionDao;

	private final IRespuestaDao respuestaDao;

	private final InicializarImagenes inicializarImagenes;

	@Transactional(readOnly = true)
	public List<Pregunta> findAll() {
		return dao.findAllOrder();
	}

	@Transactional(readOnly = true)
	public Pregunta findById(Long id) {
		return dao.findById(id).orElse(null);
	}


	@Transactional
	public void delete(Long id) {
		evaluacionDao.deleteByIdpregunta(id);
		respuestaDao.deleteByIdpregunta(id);
		dao.deleteById(id);
	}

	/**
	 * Elimina una respuesta y las evaluaciones que referencian esa opción (misma pregunta y número de respuesta).
	 */
	@Transactional
	public void deleteRespuesta(Long idPreguntaEsperado, Long idRespuesta) {
		Respuesta r = respuestaDao.findById(idRespuesta)
				.orElseThrow(() -> new NoSuchElementException("Respuesta no encontrada: " + idRespuesta));
		if (r.getIdpregunta() == null || !r.getIdpregunta().equals(idPreguntaEsperado)) {
			throw new NoSuchElementException("La respuesta no pertenece a la pregunta indicada");
		}
		if (r.getNumero() != null) {
			evaluacionDao.deleteByIdpreguntaAndNumerorespuesta(r.getIdpregunta(), r.getNumero());
		}
		respuestaDao.deleteById(idRespuesta);
	}
}
