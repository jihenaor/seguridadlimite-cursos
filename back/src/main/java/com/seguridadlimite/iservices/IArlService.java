package com.seguridadlimite.iservices;

import com.seguridadlimite.models.entity.Arl;

import java.util.List;

public interface IArlService {

	public List<Arl> findAll();

	public Arl findById(Integer id);

	public Arl save(Arl t);

	public void delete(Integer id);
}
