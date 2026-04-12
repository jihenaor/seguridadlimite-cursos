package com.seguridadlimite.models.aprendiz.application.importarAprendices;

import lombok.extern.slf4j.Slf4j;

import com.opencsv.bean.CsvToBeanBuilder;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ImportarPermisoTrabajo {

    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;

    private static final Map<String, Integer> NIVEL_ID_MAP;
/*
1	TRABAJADOR AUTORIZADO
2	COORDINADOR
4	REENTRENAMIENTO
5	ACTUALIZACION DE COORDINADOR
6	JEFE DE AREA PARA TRABAJO EN ALTURAS
 */
    static {
        NIVEL_ID_MAP = new HashMap<>();
        NIVEL_ID_MAP.put("TRABAJADOR AUTORIZADO", 1);
        NIVEL_ID_MAP.put("COORDINADOR", 2);
        NIVEL_ID_MAP.put("BASICO OPERATIVO", 3);
        NIVEL_ID_MAP.put("REENTRENAMIENTO", 4);
        NIVEL_ID_MAP.put("ADMINISTRATIVO", 5);
        NIVEL_ID_MAP.put("ADMINSTRATIVO", 5);
        NIVEL_ID_MAP.put("REENTRENO", 4);
        NIVEL_ID_MAP.put("REEENTRENAMIENTO", 4);
        NIVEL_ID_MAP.put("AVANAZADO", 7);
        NIVEL_ID_MAP.put("AVANZADO", 7);
        NIVEL_ID_MAP.put("ACTUALIZACION DE COORDINADOR", 8);
         NIVEL_ID_MAP.put("ADMINISTRATIVO PERSONAL", 9);


        NIVEL_ID_MAP.put("BASICO ADMINISTRATIVO", 5);
    }

    public void index(String content) throws BusinessException, ParseException {
        int fila = 0;
        Map<Integer, String> consecutivos = new HashMap<>();

        StringReader sr = new StringReader(content);
        List<RegistroPermiso> registros = new CsvToBeanBuilder<RegistroPermiso>(sr)
                .withType(RegistroPermiso.class)
                .withSeparator(';')
                .build()
                .parse();

        List<RegistroPermiso> registrosUnicos = registros.stream()
                .collect(Collectors.toMap(
                        RegistroPermiso::getTRABAJO, // clave única
                        r -> r,
                        (r1, r2) -> r1, // en caso de duplicado, conservar el primero
                        LinkedHashMap::new
                ))
                .values()
                .stream()
                .collect(Collectors.toList());


/*
        for (RegistroPermiso registro : registros) {
            if (registro.getTRABAJO() == null
                    || !registro.getTRABAJO().matches("-?\\d+(\\.\\d+)?")) {
                continue;
            }

            Integer id = Integer.parseInt(registro.getTRABAJO());

            PermisoTrabajoAlturas permisoTrabajoAlturas = new PermisoTrabajoAlturas();

            permisoTrabajoAlturas.setCodigoministerio(registro.getTRABAJO());
            permisoTrabajoAlturas.setFechaInicio(convertirFormatoFecha(registro.getFechaInicial()));
            permisoTrabajoAlturas.setValidodesde(convertirFormatoFecha(registro.getFechaInicial()));
            permisoTrabajoAlturas.setValidohasta(convertirFormatoFecha(registro.getFecha()));
            permisoTrabajoAlturas.setIdNivel(getNivel(registro));
            permisoTrabajoAlturas.setIdpersonaautoriza1(getIdEntrenador(registro));
        }
*/

        for (RegistroPermiso registro : registrosUnicos) {
            try {
                if (registro.getTRABAJO() == null
                        || !registro.getTRABAJO().matches("-?\\d+(\\.\\d+)?")) {
                    continue;
                }

                Integer id = Integer.parseInt(registro.getTRABAJO());

                if (consecutivos.get(id) == null) {
                    //Optional<PermisoTrabajoAlturas> p = permisoTrabajoAlturasPort.findByCodigoministerio(registro.getTRABAJO());

                    Optional<PermisoTrabajoAlturas> p = permisoTrabajoAlturasPort.findPermisosFechaInicioIdnivelIdpersonaautoriza(
                                            convertirFormatoFecha(registro.getFechaInicial()),
                                            getNivel(registro),
                                            getIdEntrenador(registro));
                    //&& pFecha.isPresent()
                    if (p.isEmpty()) {
                        log.info("Consecutivo no existe - fecha existe "
                                + registro.getTRABAJO()
                                + " " + registro.getFechaInicial()
                                + " " + registro.getNivel());
                    }
                    //  && pFecha.isEmpty()
                    if (p.isEmpty()) {
                        log.info("Permiso creado.  " + registro.getTRABAJO());
                        PermisoTrabajoAlturas permisoTrabajoAlturas = new PermisoTrabajoAlturas();

                        permisoTrabajoAlturas.setCodigoministerio(registro.getTRABAJO());
                        permisoTrabajoAlturas.setFechaInicio(convertirFormatoFecha(registro.getFechaInicial()));
                        permisoTrabajoAlturas.setValidodesde(convertirFormatoFecha(registro.getFechaInicial()));
                        permisoTrabajoAlturas.setValidohasta(convertirFormatoFecha(registro.getFecha()));
                        permisoTrabajoAlturas.setIdNivel(getNivel(registro));
                        permisoTrabajoAlturas.setIdpersonaautoriza1(getIdEntrenador(registro));
                        permisoTrabajoAlturas.setCupoinicial(0);
                        permisoTrabajoAlturas.setCupofinal(0);

                        permisoTrabajoAlturasPort.save(permisoTrabajoAlturas);
                        consecutivos.put(Integer.parseInt(registro.getTRABAJO()), "x");
                    } else {
                        PermisoTrabajoAlturas alturas = p.get();
                        if (alturas.getCodigoministerio() == null) {
                            alturas.setCodigoministerio(registro.getTRABAJO());
                            permisoTrabajoAlturasPort.save(alturas);
                        }

                    }
                }
            } catch (Exception e) {
                log.error("Se capturó una excepción: ", e);
            }
        }
    }

    private static Integer getNivel(RegistroPermiso registro) throws BusinessException {
        Integer idnivel = NIVEL_ID_MAP.get(registro.getNivel().trim());

        if (idnivel == null) {
            throw new BusinessException("Nivel no existe " + registro.getNivel() + " - " + registro.getTRABAJO());
        }

        return idnivel;
    }

    private int getIdEntrenador(RegistroPermiso registro) throws BusinessException {
        int identrenador;
        switch (registro.getEntrenador().toUpperCase().trim()) {
            case "CESAR PINEDA":
                identrenador = 4;
                break;
            case "CESAR ANDRES PINEDA OCHOA":
                identrenador = 4;
                break;
            case "ALEXANDRA BRIGITTE PEREZ GONZALEZ":
                identrenador = 5;
                break;
            case "ALEXANDRA PEREZ":
                identrenador = 5;
                break;
            case "ALEJANDRO FERNANDEZ ZAPATA":
                identrenador = 6;
                break;
            case "CESAR ANDRES PINEDA":
                identrenador = 4;
                break;
            case "ALEJANDRO FERNANDEZ":
                identrenador = 6;
                break;
            case "CONSTANZA MILENA RAMIREZ":
                identrenador = 7;
                break;
            case "V":
                identrenador = 7;
                break;
            case "NELSON DAVID GRANADOS":
                identrenador = 8;
                break;
            case "DAVID GRANADOS":
                identrenador = 8;
                break;
            case "JAMES DAVID BUITRAGO":
                identrenador = 9;
                break;
            case "LINA MURILLO":
                identrenador = 10;
                break;
            case "JAMES BUITRAGO":
                identrenador = 9;
                break;
            case "JUAN DAVID LONDOÑO":
                identrenador = 16;
                break;
            case "JUAN DAVID LONDO�O":
                identrenador = 16;
                break;
            case "JEISON NIETO":
                identrenador = 17;
                break;
            default:
                throw new BusinessException("Entrenador no existe " + registro.getEntrenador() + " " + registro.getTRABAJO());
        }

        return identrenador;
    }

    public static String convertirFormatoFecha(String fechaOriginal) throws ParseException {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");

        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");

        Date fecha = formatoEntrada.parse(fechaOriginal);

        return formatoSalida.format(fecha);
    }
}
