package com.seguridadlimite.models.aprendiz.application.importarAprendices;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

import com.opencsv.bean.CsvToBeanBuilder;
import com.seguridadlimite.iservices.IProgramaService;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.asistencia.application.RegisterAsistenciaAprendizService;
import com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz.DocumentoaprendizServiceImpl;
import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.trabajador.application.TrabajadorFindByDocumentoCu;
import com.seguridadlimite.models.trabajador.application.TrabajadorSaveCu;
import com.seguridadlimite.models.trabajador.application.TrabajadorService;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.services.AprendizServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportarAprendices {

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
        NIVEL_ID_MAP.put("BASICO OPERATIVO", 3L);
        NIVEL_ID_MAP.put("REENTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("ADMINISTRATIVO", 5L);
        NIVEL_ID_MAP.put("ADMINSTRATIVO", 5L);
        NIVEL_ID_MAP.put("REENTRENO", 4L);
        NIVEL_ID_MAP.put("REEENTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("REENTRENAMIENO", 4L);
        NIVEL_ID_MAP.put("AVANAZADO", 7L);
        NIVEL_ID_MAP.put("AVANZADO", 7L);
        NIVEL_ID_MAP.put("ACTUALIZACION DE COORDINADOR", 8L);
    NIVEL_ID_MAP.put("ADMINISTRATIVO PERSONAL", 9l);


        NIVEL_ID_MAP.put("BASICO ADMINISTRATIVO", 5L);
    }
    private final IProgramaService programaService;

    private final TrabajadorService trabajadorService;

    private final TrabajadorSaveCu trabajadorSaveCu;

    private final AprendizServiceImpl aprendizService;

    private final DocumentoaprendizServiceImpl documentoaprendizService;

    private final RegisterAsistenciaAprendizService asistenciaService;

    private final TrabajadorFindByDocumentoCu trabajadorFindByDocumento;

    private final ConsultarPermisoTrabajoAlturasUseCase consultarPermisoTrabajoAlturasUseCase;

    public void index(String content) throws BusinessException, ParseException {
        int fila = 0;

        StringReader sr = new StringReader(content);
        List<Registro> registros = new CsvToBeanBuilder<Registro>(sr)
                .withType(Registro.class)
                .withSeparator(';')
                .build()
                .parse();

        for (Registro registro : registros) {
            if (registro.getTrabajo() == null) {
                continue;
            }

            if (registro.getCedula() == null || registro.getCedula().length() < 3) {
                continue;
            }

            long idnivel = getNivel(registro, fila++);
            long idEntrenador = getIdEntrenador(registro);
        }
        int i = 1;
//        List<Aprendiz> aprendizsAll = aprendizService.findByCodigoVerificacionIsNull();


        String cedulas = "";

        List<String> documentNumbers = new ArrayList<>();

        registros.forEach(registro -> {
            documentNumbers.add(registro.getCedula());
        });

        List<Aprendiz> aprendizsAll;

        if (cedulas.length() > 1 || documentNumbers.size() > 0) {
            aprendizsAll = aprendizService.findByNumeroDocumentoIn(documentNumbers);

//            registros = registros.stream()
//                    .filter(registro -> documentNumbers.contains(registro.getCedula()))
//                    .toList();



        } else {
            aprendizsAll = aprendizService.findAll();
        }
//
        for (Registro registro : registros) {
            log.info(i++ + " Trabajo " + registro.getTrabajo());

            if (registro.getCedula() == null || registro.getCedula().length() < 3) {
                registro.setCedula(registro.getCodigo());
             }

            long idnivel = getNivel(registro, fila);
//            long idEntrenador = getIdEntrenador(registro);
//            String fechaInscripcionRegistro;

//            try {
//                fechaInscripcionRegistro = convertirFormatoFecha(registro.getFechaInicial());
//            } catch (Exception e) {
//                log.error("Se capturó una excepción: ", e);
//                continue;
//            }

            String cedula  = registro.getCedula().length() > 16
                    ? registro.getCedula().substring(0, 15)
                    : registro.getCedula();

//            List<Aprendiz> aprendizs = aprendizsAll.stream()
//                    .filter(aprendiz -> aprendiz.getTrabajador().getNumerodocumento().equals(cedula)
//                                    && aprendiz.getIdnivel() == idnivel
//                                    && registro.getCodigo().equals(aprendiz.getCodigoverificacion()))
//                    .toList();

            List<Aprendiz> aprendizs = aprendizsAll.stream()
                    .filter(aprendiz -> aprendiz.getTrabajador().getNumerodocumento().equals(cedula)
                            && aprendiz.getIdnivel() == idnivel
                            )
                    .toList();

//            if (registro.getTrabajo().length() <= 3) {
//                continue;
//            }

//            Optional<PermisoTrabajoAlturas> permisoTrabajoAlturas = consultarPermisoTrabajoAlturasUseCase.consultarCodigoministerio(registro.getTrabajo());

//            if (permisoTrabajoAlturas.isEmpty()) {
//                throw new BusinessException("Permiso no encontrado " + registro.getTrabajo());
//                log.info("Permiso no encontrado " + registro.getTrabajo());
//                continue;
//            }

                // Primero buscamos por documento y nivel para verificar duplicados
//            List<Aprendiz> aprendizsDuplicados = aprendizsAll.stream()
//                    .filter(aprendiz -> aprendiz.getTrabajador().getNumerodocumento().equals(cedula)
//                            && registro.getCodigo().equals(aprendiz.getCodigoverificacion())
//                            && aprendiz.getIdnivel() == idnivel)
//                    .toList();

            // Si hay duplicados, verificamos cuál tiene evaluación
//            if (aprendizsDuplicados.size() > 1) {
//                log.info("Mas de un aprendiz " + cedula);
//                List<Aprendiz> aprendizconEvaluacion = aprendizsDuplicados.stream()
//                        .filter(aprendiz -> aprendiz.getEteorica1() > 0
//                                || aprendiz.getEteorica2() > 0
//                                || aprendiz.getEpractica() > 0
//                                || aprendiz.getEingreso() > 0
//                                || aprendiz.getEenfasis() > 0)
//                        .toList();

//                if (aprendizconEvaluacion.isEmpty()) {
//                    // Si no hay registros con evaluación, mantener el primero y borrar los demás
//                    Aprendiz primerAprendiz = aprendizsDuplicados.get(0);
//
//                    // Borrar los registros duplicados excepto el primero
//                    for (int j = 1; j < aprendizsDuplicados.size(); j++) {
//                        aprendizService.delete(aprendizsDuplicados.get(j).getId());
//                        log.info("Borrando registro duplicado " + aprendizsDuplicados.get(j).getId());
//                    }
//                } else {
//                    for (Aprendiz aprendiz : aprendizsDuplicados) {
//                        if (aprendiz.getEteorica1() > 0
//                                || aprendiz.getEteorica2() > 0
//                                || aprendiz.getEpractica() > 0
//                                || aprendiz.getEingreso() > 0
//                                || aprendiz.getEenfasis() > 0) {
//
 //                        } else {
//                            aprendizService.delete(aprendiz.getId());
//                        }
//                    }
//                }
//            } else {
//                continue;
//            }

//            aprendizs = aprendizsAll.stream()
//                    .filter(aprendiz -> aprendiz.getTrabajador().getNumerodocumento().equals(cedula))
//                    .toList();

            if (aprendizs.isEmpty()) {

                    log.info("Nuevo");
                    crearAprendiz(
                            registro, idnivel, Optional.empty());
            } else {
                List<Aprendiz> aprendizs2 = aprendizsAll.stream()
                        .filter(aprendiz -> aprendiz.getTrabajador().getNumerodocumento().equals(cedula)
                                        && aprendiz.getIdnivel() == idnivel
                                        && registro.getCodigo().equals(aprendiz.getCodigoverificacion()))
                        .toList();

                if (aprendizs2.isEmpty()) {
                    log.info("Nuevo");
                    crearAprendiz(
                            registro, idnivel, Optional.empty());
                }
            }

//            else if (aprendizs.size() > 1){
//
 //                List<Aprendiz> aprendizsx = aprendizs.stream()
//                        .filter(aprendiz -> aprendiz.getTrabajador().getNumerodocumento().equals(cedula)
//                                && fechaInscripcionRegistro.equals(aprendiz.getFechainscripcion()))
//                        .toList();
//                if (aprendizsx.size() == 1) {
//                    aprendizsx.get(0).setCodigoverificacion(registro.getCodigo());
//                    aprendizsx.get(0).setIdPermiso(permisoTrabajoAlturas.get().getIdPermiso());
//                    aprendizService.save(aprendizsx.get(0));
//                    log.info("Actualiza");
//                } else {
//                    List<Aprendiz> aprendizsx2 = aprendizs.stream()
//                            .filter(aprendiz -> aprendiz.getTrabajador().getNumerodocumento().equals(cedula)
//                                    && registro.getCodigo().equals(aprendiz.getCodigoverificacion()))
//                            .toList();

//                }

//                log.info(cedula);
//            }

/*
                if (aprendizs.size() == 1) {
                    if (aprendizs.get(0).getIdnivel() == idnivel) {
                        if (aprendizs.get(0).getIdPermiso() == null) {
                            aprendizs.get(0).setCodigoverificacion(registro.getCodigo());
                            aprendizs.get(0).setIdPermiso(permisoTrabajoAlturas.get().getIdPermiso());
                            aprendizService.save(aprendizs.get(0));
                            log.info("Actualiza");
                        }
                    } else {
                        log.info(cedula);
                    }
                }

 */
            }


//        }
/*
        if (true) {
            continue;
        }
        StringTokenizer tokens=new StringTokenizer(content, "\n");


        while(tokens.hasMoreTokens()){
            String str = tokens.nextToken();

            int cont = 0;
            Date fechainicio = null;
            Date fechafinal = null;
            Long idnivel = 0l;
            Integer idenfasis;
            Long identrenador = 0l;
            String nombreTrabajador = "";
            String idmintrabajo = "";
            String codigo = "";
            String numerodocumento = "";
            String pago = "";

            StringTokenizer tokenInterno =new StringTokenizer(str, "\t");
            while(tokenInterno.hasMoreTokens()) {
                str=tokenInterno.nextToken();

                str = str.trim().toUpperCase();

//                22184	C050	10/23/18	10/27/18	ANDRES FELIPE TRIANA GIRALDO	1115195002	AVANZADO	AGROPECUARIO		AGRICOLA Y PECUARIO	890001662-1	cesar andres pineda\n

                switch (cont) {
                    case 0:
                        idmintrabajo = str;
                        break;
                    case 1:
                        codigo = str;
                        break;
                    case 2:
                        fechainicio = getFecha(str, null);

                        break;
                    case 3:
                        StringTokenizer tokensFechaFinal = new StringTokenizer(str, "/");

                        String mesf = tokensFechaFinal.nextToken();
                        String diaf = tokensFechaFinal.nextToken();
                        String anof = tokensFechaFinal.nextToken();

                        if (mesf.length()>2) {
                            log.info("Error en el mes " + mesf + "  Fila: " + fila);
//                            return "";
                        }

                        if (diaf.length()>2) {
                            log.info("Error en el dia " + diaf + "  Fila: " + fila);
//                            return "";
                        }

                        if (anof.length() != 4) {
                            log.info("Error en el año " + anof + "  Fila: " + fila);
//                            return "";
                        }

                        try {
                            fechafinal = new SimpleDateFormat("dd/MM/yyyy").parse(diaf + "/" + mesf + "/" + anof);
                        } catch (ParseException e) {
                            log.error("Se capturó una excepción: ", e);
                        }

                        break;
                    case 4:
                        nombreTrabajador = str.trim().replace("  ", " ");
                        break;
                    case 5:
                        numerodocumento = str.replace(".", "").replace(",", "").trim();
                        break;
                    case 6:
                        idnivel = getNivel(null);
                        break;
                    case 7:
                        if (str.trim().equals("PAGO")) {
                            pago = "S";
                        } else {
                            pago = "N";
                        }
                    case 8:
                        break;

                    default:
                        break;
                }

                cont++;
            }

            fila++;

            log.info("Fila: " + fila);
        }

 */
    }

    private void crearAprendiz(
            Registro registro,
            long idnivel,
            Optional<PermisoTrabajoAlturas> permisoTrabajoAlturas) {

        Trabajador trabajador = trabajadorFindByDocumento.findByNumerodocumento(registro.getCedula());

        if (trabajador == null) {
            return;
        }

        Aprendiz aprendiz = new Aprendiz();

        aprendiz.setIdtrabajador(trabajador.getId() == null ? null : trabajador.getId().intValue());

        aprendiz.setCodigoverificacion(registro.getCodigo());
        aprendiz.setPagocurso(registro.getPagado());
        aprendiz.setEvaluacionformacion((double) 0);
        aprendiz.setEvaluacionentrenamiento((double) 0);
        aprendiz.setCumplehoras("N");
        aprendiz.setAbonocurso("N");

        if (permisoTrabajoAlturas.isPresent()) {
            aprendiz.setIdPermiso(permisoTrabajoAlturas.get().getIdPermiso());
        }
        aprendiz.setCreateAt(new Date());
        aprendiz.setUpdateAt(new Date());
        aprendiz.setIdenfasis(0);

        aprendiz.setIdnivel(Math.toIntExact(idnivel));
//                	aprendiz.setidempresa
        aprendiz.setTrabajador(trabajador);

        aprendiz.setTieneexperienciaaltura("S");
        aprendiz.setNiveleducativo("");
        aprendiz.setCargoactual("");

        aprendiz.setEps("");
        aprendiz.setArl("");
        aprendiz.setSabeleerescribir("S");
        aprendiz.setLabordesarrolla("");

        aprendiz.setAlergias("");;
        aprendiz.setMedicamentos("");;
        aprendiz.setEnfermedades("");
        aprendiz.setLesiones("");
        aprendiz.setDrogas("");
        aprendiz.setNombrecontacto("");
        aprendiz.setTelefonocontacto("");
        aprendiz.setParentescocontacto("");;
        aprendiz.setIntentos((byte) 0);

        aprendiz.setDepartamentodomicilio("ARMENIA");
        aprendiz.setCiudaddomicilio("QUINDIO");

        aprendiz.setDirecciondomicilio("");
        if (registro.getPagado() != null && registro.getPagado().length() > 1) {
            switch (registro.getPagado().trim()) {
                case "CANCELADO":
                    aprendiz.setPagocurso("S");
                    break;
                case "CANCELO":
                    aprendiz.setPagocurso("S");
                    break;
                case "CANCEO":
                    aprendiz.setPagocurso("S");
                    break;
                case "PAGO":
                    aprendiz.setPagocurso("S");
                    break;
                case "DEBE":
                    aprendiz.setPagocurso("N");
                    break;
                case "DONACION":
                    aprendiz.setPagocurso("D");
                    break;
                case "OBSEQUIO":
                    aprendiz.setPagocurso("O");
                    break;
                default:
                    if (registro.getPagado().isEmpty()) {
                        aprendiz.setPagocurso("N");
                    } else {
                        aprendiz.setPagocurso(registro.getPagado().substring(0, 1));
                    }
            }
        }

        try {
            aprendiz = aprendizService.save(aprendiz);
        } catch (Exception e) {
//                log.info( trabajador.getId() + " " + numerodocumento + " " + grupo.getId());
            throw e;
        }
    }

    private Date getFecha(String str, Registro registro) throws BusinessException, ParseException {
        StringTokenizer tokensFecha=new StringTokenizer(str, "/");

        String mes;
        String dia;
        String ano;

        try {
            mes = tokensFecha.nextToken();
            dia = tokensFecha.nextToken();
            ano = tokensFecha.nextToken();
        } catch (Exception e) {
            log.info(registro.getCodigo());
            throw e;
        }

            if (mes.length()>2) {
//                                return "Error en el mes " + mes  + "  Fila: " + fila + " doc: " + codigo;
            }

            if (dia.length()>2) {
//                                return "Error en el dia " + dia + "  Fila: " + fila + " doc: " + codigo;
            }

            if (ano.length() != 4) {
                throw new BusinessException("Error en el año " + ano + " Reg: " + registro.getTrabajo());
//                                return "";
            }


        return new SimpleDateFormat("dd/MM/yyyy").parse(dia + "/" + mes + "/" + ano);
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
