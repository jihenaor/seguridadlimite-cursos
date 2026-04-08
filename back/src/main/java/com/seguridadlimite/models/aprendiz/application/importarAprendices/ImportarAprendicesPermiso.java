package com.seguridadlimite.models.aprendiz.application.importarAprendices;

import com.opencsv.bean.CsvToBeanBuilder;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class ImportarAprendicesPermiso {

    private static final Map<String, Long> NIVEL_ID_MAP;
/*
1	TRABAJADOR AUTORIZADO
4	REENTRENAMIENTO
6	JEFE DE AREA PARA TRABAJO EN ALTURAS
2	COORDINADOR
3	BASICO OPERATIVO (SALE)
5	ACTUALIZACION DE COORDINADOR

 */
    static {
        NIVEL_ID_MAP = new HashMap<>();
        NIVEL_ID_MAP.put("TRABAJADOR AUTORIZADO", 1L);
        NIVEL_ID_MAP.put("COORDINADOR", 2L);
        NIVEL_ID_MAP.put("COORDINADORES", 2L);
        NIVEL_ID_MAP.put("BASICO OPERATIVO", 3L);
        NIVEL_ID_MAP.put("REENTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("REENTRENAMENTO", 4L);
        NIVEL_ID_MAP.put("RENNTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("ADMINISTRATIVO", 5L);
        NIVEL_ID_MAP.put("ADMINSTRATIVO", 5L);
        NIVEL_ID_MAP.put("REENTRENO", 4L);
        NIVEL_ID_MAP.put("REEENTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("REENTRENAMIENO", 4L);
        NIVEL_ID_MAP.put("AVANAZADO", 7L);
        NIVEL_ID_MAP.put("AVANZADO", 7L);
        NIVEL_ID_MAP.put("AVAZANDO", 7L);
        NIVEL_ID_MAP.put("ACTUALIZACION DE COORDINADOR", 8L);
    NIVEL_ID_MAP.put("ADMINISTRATIVO PERSONAL", 9l);


        NIVEL_ID_MAP.put("BASICO ADMINISTRATIVO", 5L);
    }



    private IAprendizDao aprendizDao;

    @Autowired
    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;


    @Autowired
    private ConsultarPermisoTrabajoAlturasUseCase consultarPermisoTrabajoAlturasUseCase;

    @Transient
    public void index(String content) throws BusinessException, ParseException {
        int fila = 0;

        StringReader sr = new StringReader(content);
        List<Registro> registros = new CsvToBeanBuilder<Registro>(sr)
                .withType(Registro.class)
                .withSeparator(';')
                .build()
                .parse();

        int i = 0;
        List<PermisoTrabajoAlturas> ps = permisoTrabajoAlturasPort.findAll().stream()
                .filter(permisoTrabajoAlturas -> permisoTrabajoAlturas.getCodigoministerio() != null)
                .toList();
        for (Registro registro : registros) {

            System.out.println(registro.getCodigo());

            if ("G224".equals(registro.getCodigo()) || "E080".equals(registro.getCodigo())) {
                int x = 0;
            }

            if (i % 100 == 0) {
                System.out.println(i);
            }
            i++;
            if (registro.getTrabajo() == null) {
                continue;
            }
            try {
                long idnivel = getNivel(registro, fila);

                Optional<PermisoTrabajoAlturas> p = ps.stream()
                        .filter(permisoTrabajoAlturas -> permisoTrabajoAlturas.getCodigoministerio().equals( registro.getTrabajo()))
                        .findAny();

                if (p.isPresent()) {
                    List<Aprendiz> aprendizs = aprendizDao.findPorCodigoverifiacion(registro.getCodigo(), registro.getCedula(), idnivel);

                    for (Aprendiz aprendiz : aprendizs) {
                        if (aprendiz.getIdPermiso() == null) {
                            System.out.println("Actualizando");
                            aprendiz.setIdPermiso(p.get().getIdPermiso());
                            aprendizDao.save(aprendiz);
                        }
                    }
                } else {
                    System.out.println("Permiso no existe " + registro.getTrabajo());
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(registro.getTrabajo());
            }
        }
        System.out.println("termino");
    }



    private static Long getNivel(Registro registro, int fila) throws BusinessException {
        Long idnivel = NIVEL_ID_MAP.get(registro.getNivel().trim());

        if (idnivel == null) {
            throw new BusinessException("Fila: " + fila + "Nivel no existe " + registro.getNivel() + " - " + registro.getTrabajo());
        }

        return idnivel;
    }

    private long getIdEntrenador(Registro registro) throws BusinessException {
        long identrenador;

        registro.setEntrenador(registro.getEntrenador().replace("  ", " "));

        if (registro.getEntrenador().toUpperCase().contains("JUAN DAVID")) {
            identrenador = 16l;
            return identrenador;
        }

        switch (registro.getEntrenador().toUpperCase().trim()) {
            case "CESAR PINEDA":
                identrenador = 4L;
                break;
            case "CESAR ANDRES PINEDA OCHOA":
                identrenador = 4L;
                break;
            case "ALEXANDRA BRIGITTE PEREZ GONZALEZ":
                identrenador = 5L;
                break;
            case "ALEXANDRA PEREZ":
                identrenador = 5L;
                break;
            case "ALEJANDRO FERNANDEZ ZAPATA":
                identrenador = 6L;
                break;
            case "CESAR ANDRES PINEDA":
                identrenador = 4L;
                break;
            case "ALEJANDRO FERNANDEZ":
                identrenador = 6L;
                break;
            case "CONSTANZA MILENA RAMIREZ":
                identrenador = 7L;
                break;
            case "V":
                identrenador = 7L;
                break;
            case "NELSON DAVID GRANADOS":
                identrenador = 8L;
                break;
            case "DAVID GRANADOS":
                identrenador = 8L;
                break;
            case "JAMES DAVID BUITRAGO":
                identrenador = 9l;
                break;
            case "LINA MURILLO":
                identrenador = 10l;
                break;
            case "JAMES BUITRAGO":
                identrenador = 9l;
                break;
            case "JUAN DAVID LONDOÑO":
                identrenador = 16l;
                break;
            case "JUAN DAVID LONDO�O":
                identrenador = 16;
                break;
            case "JEISON NIETO":
                identrenador = 17l;
                break;
            default:
                throw new BusinessException("Entrenador no existe " + registro.getEntrenador() + " " + registro.getTrabajo());
        }

        return identrenador;
    }

    public String convertirFormatoFecha(String fechaOriginal) throws ParseException {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");

        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");

        Date fecha = formatoEntrada.parse(fechaOriginal);

        return formatoSalida.format(fecha);
    }
}
