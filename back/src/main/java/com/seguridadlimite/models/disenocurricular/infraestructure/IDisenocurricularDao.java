package com.seguridadlimite.models.disenocurricular.infraestructure;

import com.seguridadlimite.models.disenocurricular.domain.Disenocurricular;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IDisenocurricularDao extends CrudRepository<Disenocurricular, Integer> {

    /**
     * Lecturas cacheadas en {@link com.seguridadlimite.models.disenocurricular.application.FindDisenocurricularByIdnivelService#find(Long)}:
     * TTL máximo ~30 días y se invalida la entrada del nivel al persistir un {@link Disenocurricular} de ese nivel vía
     * {@link com.seguridadlimite.models.disenocurricular.application.FindDisenocurricularByIdnivelService#save(com.seguridadlimite.models.disenocurricular.domain.Disenocurricular)}.
     */
    List<Disenocurricular> findByIdnivelOrderByDiaAsc(Long idnivel);
}
