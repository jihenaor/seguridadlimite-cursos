package com.seguridadlimite.iservices;

import java.util.List;

import com.seguridadlimite.models.programa.model.Programa;

public interface IProgramaService {

	public List<Programa> findAll();

	public Programa findById(Long id);

	public Programa save(Programa programa);

	public void delete(Long id);
}
