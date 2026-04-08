package com.seguridadlimite.models.eps.application;

import com.seguridadlimite.models.eps.domain.Eps;

import java.util.List;

public interface IEpsService {

	public List<Eps> findAll();

	public Eps findById(Long id);

	public Eps save(Eps t);

	public void delete(Long id);
}
