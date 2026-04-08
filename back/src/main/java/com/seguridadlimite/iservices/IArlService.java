package com.seguridadlimite.iservices;

import com.seguridadlimite.models.entity.Arl;

import java.util.List;

public interface IArlService {

	public List<Arl> findAll();

	public Arl findById(Long id);

	public Arl save(Arl t);

	public void delete(Long id);
}
