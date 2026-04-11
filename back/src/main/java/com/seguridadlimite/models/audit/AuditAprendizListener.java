package com.seguridadlimite.models.audit;

import lombok.extern.slf4j.Slf4j;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;


@Slf4j
public class AuditAprendizListener {
    @PostPersist
    @PostUpdate
    public void onPostPersist(Aprendiz aprendiz) {
        log.info(aprendiz.getId());
    }
}
