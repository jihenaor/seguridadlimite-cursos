package com.seguridadlimite.models.pregunta.application.findByGrupo;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import com.seguridadlimite.models.pregunta.application.inicializarimagenes.InicializarImagenes;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarPreguntasGrupoService {

	private final IPreguntaDao dao;

	private final InicializarImagenes inicializarImagenes;

	public List<Pregunta> find(Long idgrupo) {
		List<Pregunta> l = dao.findByIdgrupo(idgrupo);

		inicializarImagenes.inicializarImagenes(l);

		return l;
	}
}
