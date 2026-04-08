package com.seguridadlimite.models.audit;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;


public class AuditAprendizListener {
    @PostPersist
    @PostUpdate
    public void onPostPersist(Aprendiz aprendiz) {
        System.out.println(aprendiz.getId());
    }
}
