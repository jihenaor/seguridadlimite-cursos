package com.seguridadlimite.models.aprendiz.application.importarAprendices;

import lombok.RequiredArgsConstructor;

import com.opencsv.bean.CsvToBeanBuilder;
import com.seguridadlimite.models.asistencia.application.RegisterAsistenciaAprendizService;
import com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz.DocumentoaprendizServiceImpl;
import com.seguridadlimite.models.trabajador.application.TrabajadorFindByDocumentoCu;
import com.seguridadlimite.models.trabajador.application.TrabajadorSaveCu;
import com.seguridadlimite.models.trabajador.application.TrabajadorService;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.services.AprendizServiceImpl;
import com.seguridadlimite.springboot.backend.apirest.services.EvaluacionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImportarTrabajador {

    private static final Map<String, Long> NIVEL_ID_MAP;

    static {
        NIVEL_ID_MAP = new HashMap<>();
        NIVEL_ID_MAP.put("TRABAJADOR AUTORIZADO", 1L);
        NIVEL_ID_MAP.put("COORDINADOR", 2L);
        NIVEL_ID_MAP.put("BASICO OPERATIVO", 3L);
        NIVEL_ID_MAP.put("REENTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("ADMINISTRATIVO", 5L);
        NIVEL_ID_MAP.put("REENTRENO", 4L);
        NIVEL_ID_MAP.put("REEENTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("AVANAZADO", 7L);
        NIVEL_ID_MAP.put("AVANZADO", 7L);
        NIVEL_ID_MAP.put("ACTUALIZACION DE COORDINADOR", 8L);
    NIVEL_ID_MAP.put("ADMINISTRATIVO PERSONAL", 9l);


        NIVEL_ID_MAP.put("BASICO ADMINISTRATIVO", 5L);
    }


    private final TrabajadorService trabajadorService;

    private final TrabajadorSaveCu trabajadorSaveCu;

    private final AprendizServiceImpl aprendizService;

    private final DocumentoaprendizServiceImpl documentoaprendizService;

    private final RegisterAsistenciaAprendizService asistenciaService;

    private final EvaluacionServiceImpl evaluacionService;

    private final TrabajadorFindByDocumentoCu trabajadorFindByDocumento;

    public void index(String content) throws BusinessException, ParseException {
        int fila = 0;

        StringReader sr = new StringReader(content);
        List<RegistroTrabajador> registros = new CsvToBeanBuilder<RegistroTrabajador>(sr)
                .withType(RegistroTrabajador.class)
                .withSeparator(';')
                .build()
                .parse();

        for (RegistroTrabajador registro : registros) {
            System.out.println("Importando");

            getTrabajador(registro);
        }

    }

    private void getTrabajador(RegistroTrabajador registro) {
        Trabajador trabajador = trabajadorFindByDocumento.findByNumerodocumento(registro.getCedula().length() > 16 ? registro.getCedula().substring(0, 15) : registro.getCedula());

        if (trabajador == null) {
            trabajador = new Trabajador();

            trabajador.setTipodocumento("CC");
            trabajador.setNumerodocumento(registro.getCedula().length() > 16 ? registro.getCedula().substring(0, 15) : registro.getCedula());
            trabajador.setPrimernombre(registro.getNombre());
            trabajador.setSegundonombre("");
            trabajador.setPrimerapellido("");
            trabajador.setSegundoapellido("");

            trabajador.setGenero("M");
            trabajador.setNacionalidad("CO");
            trabajador.setTiposangre("O+");

            trabajador.setOcupacion("");

            trabajador.setCelular("");

            trabajador.setCorreoelectronico("");


            trabajador.setCreateAt(new Date());
            trabajador.setUpdateAt(new Date());
            trabajador.setAdjuntodocumento("N");
            try {
                trabajadorSaveCu.save(trabajador);
            } catch (Exception e) {
                System.out.println(registro.getCedula());
                throw e;
            }
        }
    }
}
