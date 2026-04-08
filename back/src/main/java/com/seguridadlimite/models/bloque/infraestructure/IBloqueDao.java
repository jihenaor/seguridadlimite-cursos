package com.seguridadlimite.models.bloque.infraestructure;

import com.seguridadlimite.models.bloque.model.Bloque;
import org.springframework.data.repository.CrudRepository;

public interface IBloqueDao extends CrudRepository<Bloque, Long>{

}
