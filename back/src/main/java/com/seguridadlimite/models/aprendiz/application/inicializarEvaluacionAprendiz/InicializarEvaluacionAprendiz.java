package com.seguridadlimite.models.aprendiz.application.inicializarEvaluacionAprendiz;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.evaluacion.application.DeleteEvaluacionService;
import com.seguridadlimite.models.parametros.application.UpdateEvaluationDate.FindParametrosById;
import com.seguridadlimite.models.parametros.dominio.Parametros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InicializarEvaluacionAprendiz {

    private final IAprendizDao aprendizDao;

    private final FindParametrosById findParametrosById;

    private final DeleteEvaluacionService deleteEvaluacionService;

    @Transactional
    public void update(String numeroDocumento,
                       String tipoEvaluacion,
                       Long idaprendiz)  {
        Parametros parametros = findParametrosById.find();
        Aprendiz aprendiz;

        if (idaprendiz == null) {
            // aprendiz = aprendizDao.findFechaInscripcionNumeroDocumento(parametros.getFechainscripcion(), numeroDocumento);
            aprendiz=null;
        } else {
            aprendiz = aprendizDao.findById(AprendizId.toInteger(idaprendiz)).orElseThrow();
        }

        switch (tipoEvaluacion) {
            case "T":
                aprendizDao.updateEvaluacionteorica1((double)0, aprendiz.getId());
                aprendizDao.updateEvaluacionteorica2((double)0, aprendiz.getId());

                deleteEvaluacionService.delete("E", AprendizId.toLong(aprendiz.getId()));
                break;
            case "I":
                aprendizDao.updateEvaluacionIngreso((double)0, aprendiz.getId());
                break;
            case "E":
                aprendizDao.updateEvaluacionteoricaEnfasis((double)0, aprendiz.getId());
                break;
        }

        deleteEvaluacionService.delete(tipoEvaluacion, AprendizId.toLong(aprendiz.getId()));
    }
}


