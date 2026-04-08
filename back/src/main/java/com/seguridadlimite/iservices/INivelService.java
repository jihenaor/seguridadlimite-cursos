package com.seguridadlimite.iservices;

import com.seguridadlimite.models.nivel.domain.Nivel;

import java.util.List;

public interface INivelService {

	public List<Nivel> findAll();

	public List<Nivel> findActivos();

	public Nivel findById(Long id);

	public Nivel save(Nivel programa);

	public void delete(Long id);
}
