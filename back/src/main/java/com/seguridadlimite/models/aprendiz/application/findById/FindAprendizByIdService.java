package com.seguridadlimite.models.aprendiz.application.findById;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class FindAprendizByIdService {

	private IAprendizDao aprendizDao;

	public Aprendiz find(Long idaprendiz) throws BusinessException {
		return aprendizDao.findById(AprendizId.toInteger(idaprendiz))
				.orElseThrow(() -> new NoSuchElementException("No se encontró el aprendiz con el ID proporcionado"));
	}
}
