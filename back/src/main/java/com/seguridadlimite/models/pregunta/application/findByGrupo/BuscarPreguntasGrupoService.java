package com.seguridadlimite.models.pregunta.application.findByGrupo;

import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import com.seguridadlimite.models.pregunta.application.inicializarimagenes.InicializarImagenes;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarPreguntasGrupoService {

	@Autowired
	private IPreguntaDao dao;

	@Autowired
	private InicializarImagenes inicializarImagenes;

	public List<Pregunta> find(Long idgrupo) {
		List<Pregunta> l = dao.findByIdgrupo(idgrupo);

		inicializarImagenes.inicializarImagenes(l);

		return l;
	}
}
