package com.seguridadlimite.iservices;

import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;

import java.util.List;

public interface IGrupopreguntaService {

	public List<Grupopregunta> findAll();

	public Grupopregunta findById(Long id);

	public Grupopregunta save(Grupopregunta t);

	public void delete(Long id);
}
