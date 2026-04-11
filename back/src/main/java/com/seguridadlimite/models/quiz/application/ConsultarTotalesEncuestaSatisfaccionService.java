package com.seguridadlimite.models.quiz.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.quiz.infraestructure.IQuizDao;
import com.seguridadlimite.models.quiz.infraestructure.projection.TotalesEncuestaSatisfaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarTotalesEncuestaSatisfaccionService {

	private final IQuizDao dao;

	public List<TotalesEncuestaSatisfaccion> find() {

		return dao.find();
	}

}
