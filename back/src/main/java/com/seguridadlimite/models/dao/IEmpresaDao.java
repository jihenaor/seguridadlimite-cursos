package com.seguridadlimite.models.dao;

import com.seguridadlimite.models.entity.Empresa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IEmpresaDao extends CrudRepository<Empresa, Long>{
	@Query("select u from Empresa u where u.numerodocumento = ?1")
	Empresa findBynumerodocumento(String numerodocumento);
	  
	@Query("select u from Empresa u where id > 0"
	  		+ "order by nombre")
	List<Empresa> findAll();

	@Query("select u from Empresa u where seleccion = 'S'"
			+ "order by nombre")
	List<Empresa> findSeleccionada();

	Optional<Empresa> findFirstByNombreContainingIgnoreCase(String nombre);

}
