package com.seguridadlimite.models.aprendiz.application.extraerCertificado;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.util.EncodeFileToBase64;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExtraerCertificadoTrabajadorServiceImpl implements ExtraerCertificadoTrabajadorService {


    private final IAprendizDao aprendizDao;

    private final EncodeFileToBase64 encodeFile;


    public String buscar(Long idAprendiz) throws BusinessException {

        Aprendiz aprendiz = aprendizDao.findById(AprendizId.toInteger(idAprendiz)).orElseThrow();

        if (aprendiz.getCodigoverificacion() == null || aprendiz.getCodigoverificacion().isEmpty()) {
            throw new BusinessException("No existe código de verificación");
        }

        Optional<String> base64 = encodeFile.encode("C",
                aprendiz.getCodigoverificacion());

        if (base64.isPresent()) {
            return base64.get();
        } else {
            throw new BusinessException("No se encontro el certificado del aprendiz");
        }
    }
}