package com.seguridadlimite.models.aprendiz.application.extraerFoto;

import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.util.EncodeFileToBase64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExtraerFotoTrabajadorService {
    private final EncodeFileToBase64 encodeFile;
    private final ITrabajadorDao trabajadorDao;

    public String buscar(Long id) throws BusinessException {

        Trabajador trabajador = trabajadorDao.findById(id).orElseThrow(() -> new RuntimeException("Trabajador no existe"));
        Optional<String> base64 = encodeFile.encode("F", id.toString());

        if (base64.isPresent()) {

            if (trabajador.getFoto() == null) {
                trabajadorDao.actualizarFoto(id, "S");
            }

            return base64.get();
        } else {
            throw new BusinessException("No se encontro la foto del trabajador, Solicita al aprendiz que se tome una foto.");
        }
    }
}