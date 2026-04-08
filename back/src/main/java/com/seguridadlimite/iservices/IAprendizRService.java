package com.seguridadlimite.iservices;

import java.util.List;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;

public interface IAprendizRService {

	public List<Aprendiz> findAll();

	public Aprendiz findById(Long id);

	public List<Aprendiz> findByNumerodocumento(String numerodocumento);

	public Aprendiz save(Aprendiz aprendiz);

	public void delete(Long id);
}