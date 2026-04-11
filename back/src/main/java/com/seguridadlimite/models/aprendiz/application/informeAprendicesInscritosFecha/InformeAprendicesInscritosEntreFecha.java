package com.seguridadlimite.models.aprendiz.application.informeAprendicesInscritosFecha;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.documentoaprendiz.domain.Documentoaprendiz;
import com.seguridadlimite.models.documentoaprendiz.findDocumentosAprendiz.FindDocumentoaprendizServiceImpl;
import com.seguridadlimite.util.DateUtil;
import com.seguridadlimite.util.UtilidadesPaises;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InformeAprendicesInscritosEntreFecha {

    private IAprendizDao iAprendizDao;

    private FindDocumentoaprendizServiceImpl documentoaprendizService;


    public List<RegistroInformeInscritosDto> generarInformeAprendicesInscritosEntreFecha(
            String fechaInicial,
            String fechaFinal,
            String origen) {

        List<Aprendiz> aprendizs = null;

        switch (origen) {
            case "INSCRIPCION":
                aprendizs = iAprendizDao.findAprendicesFechaInscripcion(
                        fechaInicial,
                        fechaFinal);
                break;
            case "FECHA-ULTIMA-AISTENCIA":
                aprendizs = iAprendizDao.findAprendicesFechaUltimaAsistencia(
                        DateUtil.stringToLocalDate(fechaInicial),
                        DateUtil.stringToLocalDate(fechaFinal));
        }

        return generarDatosInforme(aprendizs);
    }

    private List<RegistroInformeInscritosDto>  generarDatosInforme(List<Aprendiz> aprendizs) {
        List<RegistroInformeInscritosDto> registroInformeInscritosDtos = new ArrayList<>();

        aprendizs.forEach(aprendiz -> {

            registroInformeInscritosDtos.add(
                    RegistroInformeInscritosDto
                            .builder()
                            .idaprendiz(AprendizId.toLong(aprendiz.getId()))
                            .tipoDocumento(aprendiz.getTrabajador().getTipodocumento())
                            .documento(aprendiz.getTrabajador().getNumerodocumento())
                            .primerNombre(aprendiz.getTrabajador().getPrimernombre())
                            .segundoNombre(aprendiz.getTrabajador().getSegundonombre())
                            .primerApellido(aprendiz.getTrabajador().getPrimerapellido())
                            .segundoApellido(aprendiz.getTrabajador().getSegundoapellido())
                            .genero(aprendiz.getTrabajador().getGenero())
                            .paisNacimiento(UtilidadesPaises.getPais(aprendiz.getTrabajador().getNacionalidad()))
                            .fechaNacimiento(aprendiz.getTrabajador().getFechanacimiento())
                            .nivelEducativo(getNivelEducativo(aprendiz.getNiveleducativo()))
                            .cargoActual(aprendiz.getCargoactual())
                            .sector(aprendiz.getEnfasis().getNombre())
                            .empleador(aprendiz.getEmpresa())
                            .arl(aprendiz.getArl())
                            .build()
            );
        });

        return registroInformeInscritosDtos;
    }

    public static String getNivelEducativo(String nivelEducativo) {
        if (nivelEducativo == null) {
            return "Nivel educativo no definido";
        }
        switch (nivelEducativo) {
            case "1":
                return "Primaria";
            case "2":
                return "Bachillerato";
            case "3":
                return "Técnico";
            case "4":
                return "Tecnólogo";
            case "5":
                return "Universitaria";
            default:
                return "Nivel educativo desconocido";
        }
    }

    private String seleccionarDocumentosAprendiz(Aprendiz aprendiz) {
        List<Documentoaprendiz> documentoaprendizs = documentoaprendizService.findByIdAprendiz(AprendizId.toLong(aprendiz.getId()));

        StringBuilder documentos = new StringBuilder();

        for (Documentoaprendiz documento : documentoaprendizs) {
            documentos.append(documento.getDocumento().getNombre()).append(", ");
        }

        if (!documentos.isEmpty()) {
            documentos.delete(documentos.length() - 2, documentos.length());
        }

        return documentos.toString();
    }

    private String descripcionPago(Aprendiz aprendiz) {
        if (aprendiz.getPagocurso() == null)  {
            return "";
        }
        return switch (aprendiz.getPagocurso().trim()) {
            case "S" -> "CANCELADO";
            case "N" -> "DEBE";
            case "D" -> "DONACION";
            case "O" -> "OBSEQUIO";
            default -> "";
        };
    }


    /*


        Programa programa = programaService.findById(1L);

        for (Registro registro : registros) {
            if (registro.getIdMinTrabajo() == null) {
                continue;
            }

            if (registro.getCedula() == null || registro.getCedula().length() < 3) {
                registro.setCedula(registro.getCodigo());
            }

            long idnivel = getNivel(registro);
            long idEntrenador = getIdEntrenador(registro);
        }

        for (Registro registro : registros) {
            System.out.println("Importando");
            if (registro.getIdMinTrabajo() == null) {
                continue;
            }

            if (registro.getCedula() == null || registro.getCedula().length() < 3) {
                registro.setCedula(registro.getCodigo());
             }

            long idnivel = getNivel(registro);
            long idEntrenador = getIdEntrenador(registro);

            Grupo grupo = getGrupo(idnivel, registro.getIdMinTrabajo(),
                    idEntrenador, registro);

            Trabajador trabajador = getTrabajador(registro);
            crearAprendiz(grupo, trabajador, registro, idnivel, registro.getCodigo());
        }

        if (true) {
            return;
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
                            System.out.println("Error en el mes " + mesf + "  Fila: " + fila);
//                            return "";
                        }

                        if (diaf.length()>2) {
                            System.out.println("Error en el dia " + diaf + "  Fila: " + fila);
//                            return "";
                        }

                        if (anof.length() != 4) {
                            System.out.println("Error en el año " + anof + "  Fila: " + fila);
//                            return "";
                        }

                        try {
                            fechafinal = new SimpleDateFormat("dd/MM/yyyy").parse(diaf + "/" + mesf + "/" + anof);
                        } catch (ParseException e) {
                            e.printStackTrace();
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

//                System.out.println(str);
                cont++;
            }

            fila++;

            System.out.println("Fila: " + fila);
        }

    private void crearAprendiz(
            Grupo grupo,
            Trabajador trabajador,
            Registro registro,
            long idnivel,
            String codigo) {
        Aprendiz aprendiz = aprendizService.findBynumerodocumentogrupo(
                trabajador.getNumerodocumento(),
                grupo.getId());

        if (aprendiz == null) {
            aprendiz = new Aprendiz();

            aprendiz.setIdtrabajador(trabajador.getId());
//                	aprendiz,setfechaverificacion
            aprendiz.setCodigoverificacion(registro.getCodigo());
            aprendiz.setPagocurso(registro.getPagado());
            aprendiz.setEvaluacionformacion((double) 0);
            aprendiz.setEvaluacionentrenamiento((double) 0);
            aprendiz.setCumplehoras("N");
            aprendiz.setAbonocurso("N");

            aprendiz.setCreateAt(new Date());
            aprendiz.setUpdateAt(new Date());
            aprendiz.setIdenfasis(0l);
            aprendiz.setIdgrupo(grupo.getId());
            aprendiz.setIdnivel(idnivel);
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
            aprendiz.setIntentos(0);

            aprendiz.setDepartamentodomicilio("ARMENIA");
            aprendiz.setCiudaddomicilio("QUINDIO");

            aprendiz.setDirecciondomicilio("");
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

            try {
                aprendiz = aprendizService.save(aprendiz);
            } catch (Exception e) {
//                System.out.println( trabajador.getId() + " " + numerodocumento + " " + grupo.getId());
                throw e;
            }

        } else {
            aprendiz.setCodigoverificacion(codigo);
            aprendizService.save(aprendiz);
        }
    }

    private Trabajador getTrabajador(Registro registro) {
        Trabajador trabajador = trabajadorService.findByNumerodocumento(registro.getCedula().length() > 16 ? registro.getCedula().substring(0, 15) : registro.getCedula());

        if (trabajador == null) {
            trabajador = new Trabajador();
        }
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
            trabajador = trabajadorService.save(trabajador);
        } catch (Exception e) {
            System.out.println(registro.getCedula());
            throw e;
        }
        return trabajador;
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
            System.out.println(registro.getCodigo());
            throw e;
        }

            if (mes.length()>2) {
//                                return "Error en el mes " + mes  + "  Fila: " + fila + " doc: " + codigo;
            }

            if (dia.length()>2) {
//                                return "Error en el dia " + dia + "  Fila: " + fila + " doc: " + codigo;
            }

            if (ano.length() != 4) {
                throw new BusinessException("Error en el año " + ano + " Reg: " + registro.getIdMinTrabajo());
//                                return "";
            }


        return new SimpleDateFormat("dd/MM/yyyy").parse(dia + "/" + mes + "/" + ano);


    }

    private static Long getNivel(Registro registro) throws BusinessException {
        Long idnivel = NIVEL_ID_MAP.get(registro.getNivel().trim());

        if (idnivel == null) {
            throw new BusinessException("Nivel no existe " + registro.getNivel() + " - " + registro.getIdMinTrabajo());
        }

        return idnivel;
    }

    private Grupo getGrupo(Long idnivel,
                           String idmintrabajo, Long identrenador,
                           Registro registro) throws BusinessException, ParseException {
        Date fechainicio;
        Date fechaFinal;

        fechainicio = getFecha(registro.getFechaInicial(), registro);
        fechaFinal = getFecha(registro.getFecha(), registro);

        Long idprograma = 1l;
        Grupo grupo = grupoService.findProgramaNivel(idprograma, idnivel, idmintrabajo);

        if (grupo == null){
            grupo = new Grupo();

            grupo.setCupoinicial(0);
            grupo.setCupofinal(0);;
            grupo.setCodigoministerio(idmintrabajo);
            grupo.setIdentrenador(identrenador);;

            grupo.setIdprograma(idprograma);
            grupo.setIdentrenadorinduccion(identrenador);
            grupo.setIdsupervisor(identrenador);
            grupo.setIdnivel(idnivel);
            grupo.setFechainicio(fechainicio);
            grupo.setFechafinal(fechaFinal);
            grupo.setAucodestad("A");
            grupo.setUpdateAt(new Date());
            grupo.setCreateAt(new Date());
            grupo.setJornada("F");
        } else {
            grupo.setIdentrenador(identrenador);
            grupo.setIdsupervisor(identrenador);
            grupo.setIdentrenadorinduccion(identrenador);

        }
        grupo = grupoService.save(grupo);
        return grupo;
    }



     */
}
