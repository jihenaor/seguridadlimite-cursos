package com.seguridadlimite.iservices;

import com.seguridadlimite.models.nivel.domain.Nivel;

import java.util.List;

public interface INivelInscripcionService {

	List<Nivel> findActivos();
}
