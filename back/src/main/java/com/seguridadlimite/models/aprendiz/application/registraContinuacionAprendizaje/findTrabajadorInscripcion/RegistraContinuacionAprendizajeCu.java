package com.seguridadlimite.models.aprendiz.application.registraContinuacionAprendizaje.findTrabajadorInscripcion;

import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistraContinuacionAprendizajeCu {

	private final IAprendizDao aprendizDao;

	@Transactional
	public void registrarContinuaAprendizaje(long idAprendiz)  {
		aprendizDao.findById(idAprendiz).orElseThrow(() -> new RuntimeException("Id aprendiz no existe"));

		aprendizDao.updatefechaUltimaAsistencia(DateUtil.getCurrentDate(), idAprendiz);
	}

}
