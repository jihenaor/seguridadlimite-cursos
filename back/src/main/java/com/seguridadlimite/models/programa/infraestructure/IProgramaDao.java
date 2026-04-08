package com.seguridadlimite.models.programa.infraestructure;

import org.springframework.data.repository.CrudRepository;
import com.seguridadlimite.models.programa.model.Programa;

public interface IProgramaDao extends CrudRepository<Programa, Long> {
} 