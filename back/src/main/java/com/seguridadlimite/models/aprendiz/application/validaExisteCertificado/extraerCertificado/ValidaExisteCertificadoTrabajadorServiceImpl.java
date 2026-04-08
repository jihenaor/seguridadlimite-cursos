package com.seguridadlimite.models.aprendiz.application.validaExisteCertificado.extraerCertificado;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.util.EncodeFileToBase64;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ValidaExisteCertificadoTrabajadorServiceImpl implements ValidaExisteCertificadoTrabajadorService {


    private final IAprendizDao aprendizDao;

    private final EncodeFileToBase64 encodeFile;


    @Transactional(readOnly = true)
    public List<String> buscar() throws BusinessException {
        List<String> ids = new ArrayList<>();
        Iterable<Aprendiz> a = aprendizDao.findAll();

        a.forEach(aprendiz -> {
            if (aprendiz.getCodigoverificacion() != null) {
                if (encodeFile.encode("C", aprendiz.getCodigoverificacion()).isEmpty()) {
                    ids.add(aprendiz.getCodigoverificacion());
                }
            }
        });

        return obtenerRangos(ids);
    }

    public List<String> obtenerRangos(List<String> datos) {
        List<String> rangos = new ArrayList<>();
        if (datos == null || datos.isEmpty()) {
            return rangos;
        }

        String inicioRango = datos.get(0);
        String finRango = datos.get(0);

        for (int i = 1; i < datos.size(); i++) {
            String datoActual = datos.get(i);
            String datoAnterior = datos.get(i - 1);

            if (esSiguienteDato(datoAnterior, datoActual)) {
                finRango = datoActual;
            } else {
                rangos.add(inicioRango.trim().equals(finRango.trim()) ?
                        inicioRango :
                        inicioRango + " - " + finRango);
                inicioRango = datoActual;
                finRango = datoActual;
            }
        }

        rangos.add(inicioRango.trim().equals(finRango.trim()) ?
                inicioRango :
                inicioRango + " - " + finRango);

        return rangos;
    }

    public static boolean esSiguienteDato(String datoAnterior, String datoActual) {
        String prefixAnterior = datoAnterior.substring(0, 1);
        String prefixActual = datoActual.substring(0, 1);
        int numAnterior = Integer.parseInt(datoAnterior.substring(1));
        int numActual = Integer.parseInt(datoActual.substring(1));

        return prefixAnterior.equals(prefixActual) && numActual == numAnterior + 1;
    }
}