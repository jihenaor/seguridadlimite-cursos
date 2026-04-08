package com.seguridadlimite.models.disenocurricular.infraestructure;

import com.seguridadlimite.models.disenocurricular.domain.Disenocurricular;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IDisenocurricularDao extends CrudRepository<Disenocurricular, Long> {

    List<Disenocurricular> findByIdnivelOrderByDiaAsc(Long idnivel);
}
