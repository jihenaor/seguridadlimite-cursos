package com.seguridadlimite.models.aprendiz.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindAprendicesByIds {
    private IAprendizDao aprendizDao;

    @Transactional(readOnly = true)
    public List<Aprendiz> findByIds(List<Long> ids, Long idnivel) {
        List<Integer> intIds = ids.stream().map(AprendizId::toInteger).collect(Collectors.toList());
        return aprendizDao.findAprendicesByIds(intIds, idnivel);
    }
}
