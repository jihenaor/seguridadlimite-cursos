package com.seguridadlimite.models.aprendiz.application.inscribiraprendiz;

import com.seguridadlimite.models.aprendiz.application.inicializarEvaluacionAprendiz.InicializarEvaluacionAprendiz;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.dao.IEmpresaDao;
import com.seguridadlimite.models.entity.Empresa;
import com.seguridadlimite.models.entity.TrabajadorInscripcionPojo;
import com.seguridadlimite.models.nivel.application.FindNivelByIdService;
import com.seguridadlimite.models.nivel.domain.Nivel;
import com.seguridadlimite.models.trabajador.application.TrabajadorFindByDocumentoCu;
import com.seguridadlimite.models.trabajador.application.TrabajadorSaveCu;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.services.AprendizServiceImpl;
import com.seguridadlimite.util.DateUtil;
import com.seguridadlimite.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscribirAprendiz {

    private final TrabajadorFindByDocumentoCu trabajadorFindByDocumentoCu;
    private final TrabajadorSaveCu trabajadorSaveCu;
    private final AprendizServiceImpl aprendizService;
    private final InicializarEvaluacionAprendiz inicializarEvaluacionAprendiz;
    private final FindNivelByIdService findNivelByIdService;
    private final IEmpresaDao iEmpresaDao;

    public Aprendiz inscribir(TrabajadorInscripcionPojo trabajadorInscripcionPojo) {
        validarDatosInscripcion(trabajadorInscripcionPojo);

        Trabajador trabajador = actualizarTrabajador(trabajadorInscripcionPojo);

        Aprendiz aprendiz = actualizarAprendiz(trabajadorInscripcionPojo, trabajador);

        Nivel nivel = (findNivelByIdService.findById(
                aprendiz.getIdnivel() == null ? null : aprendiz.getIdnivel().longValue()));

        aprendiz.setNivel(nivel);
        aprendiz.setTotalhoras(nivel.getDuraciontotal());

        return aprendiz;
    }

    private void validarDatosInscripcion(TrabajadorInscripcionPojo trabajadorInscripcionPojo) {
        if (trabajadorInscripcionPojo.getFechanacimiento() == null
                || trabajadorInscripcionPojo.getFechanacimiento().length() != 10
                || DateUtil.calcularEdad(trabajadorInscripcionPojo.getFechanacimiento()) > 80
                || DateUtil.calcularEdad(trabajadorInscripcionPojo.getFechanacimiento()) < 15) {
            throw new BusinessException("La fecha nacimiento " + trabajadorInscripcionPojo.getFechanacimiento() + " no es valida");
        }
    }

    private Trabajador actualizarTrabajador(TrabajadorInscripcionPojo trabajadorInscripcionPojo) {
        Trabajador trabajador;
        trabajador = inicializarAtributosTrabajador(trabajadorInscripcionPojo);

        trabajador = trabajadorSaveCu.save(trabajador);
        return trabajador;
    }

    private Aprendiz actualizarAprendiz(TrabajadorInscripcionPojo trabajadorInscripcion,
                                        Trabajador trabajador)  {
        Aprendiz aprendiz;
        if (trabajadorInscripcion.getIdaprendiz() == null) {
            aprendiz = new Aprendiz();
            aprendiz.setIdtrabajador(trabajador.getId() == null ? null : trabajador.getId().intValue());
        } else {
            aprendiz = aprendizService.findById(AprendizId.toLong(trabajadorInscripcion.getIdaprendiz()));

            if (!Objects.equals(aprendiz.getIdnivel(), trabajadorInscripcion.getIdnivel()) ||
                    !Objects.equals(aprendiz.getIdenfasis(), trabajadorInscripcion.getIdenfasis())
            ) {
                inicializarEvaluacionAprendiz.update("", "T", AprendizId.toLong(aprendiz.getId()));

                aprendiz.setEteorica1((double) 0);
                aprendiz.setEteorica2((double) 0);
            }
        }

        inicializarAtributosAprendiz(trabajadorInscripcion, trabajador, aprendiz);

        if (aprendiz.getEmpresa() != null) {
            Optional<Empresa> empresa = iEmpresaDao.findFirstByNombreContainingIgnoreCase(aprendiz.getEmpresa());

            if (empresa.isPresent()) {
                aprendiz.setNit(empresa.get().getNumerodocumento());
                aprendiz.setRepresentantelegal(empresa.get().getNombrerepresentantelegal());
            }
        }
/*
        if (aprendiz.getId() == null && existeTrabajadorGrupo(aprendiz.getTrabajador().getNumerodocumento(), aprendiz.getIdgrupo())) {
            throw new BusinessException("El trabajador ya se encuentra inscrito en el grupo");
        }
*/
        aprendiz = aprendizService.save(aprendiz);
        return aprendizService.findById(AprendizId.toLong(aprendiz.getId()));
    }

    private void inicializarAtributosAprendiz(TrabajadorInscripcionPojo inscripcionPojo,
                                                     Trabajador trabajador,
                                                     Aprendiz aprendiz) {

        aprendiz.setIdnivel(inscripcionPojo.getIdnivel());
        aprendiz.setIdenfasis(inscripcionPojo.getIdenfasis());
        aprendiz.setLabordesarrolla(inscripcionPojo.getLabordesarrolla());
        aprendiz.setEmpresa(inscripcionPojo.getEmpresa());
        aprendiz.setNit(inscripcionPojo.getNit());

        if (!inscripcionPojo.isAprendizContinuaAprendizaje()) {
            aprendiz.setFechainscripcion(DateUtil.toDayInString());
            aprendiz.setFechalimiteinscripcion(DateUtil.dateInDate(Optional.of(1)));
        }
        aprendiz.setFechaUltimaAsistencia(DateUtil.getCurrentDate());


        if (!inscripcionPojo.isInscripcionporlector()) {
            aprendiz.setTrabajador(trabajador);

            aprendiz.setRepresentantelegal(inscripcionPojo.getRepresentantelegal());
            aprendiz.setTieneexperienciaaltura(inscripcionPojo.getTieneexperienciaaltura());
            aprendiz.setLabordesarrolla(inscripcionPojo.getLabordesarrolla());
    /*
            aprendiz.setTipovinculacionlaboral(inscripcionPojo.getTipovinculacionlaboral());
            aprendiz.setRegimenafiliacionseguridadsocial(inscripcionPojo.getRegimenafiliacionseguridadsocial());
            aprendiz.setDocumentoidentidad(inscripcionPojo.getDocumentoidentidad());
            aprendiz.setUltimopagoseguridadsocial(inscripcionPojo.getUltimopagoseguridadsocial());
            aprendiz.setAfiliacionseguridadsocial(inscripcionPojo.getAfiliacionseguridadsocial());
            aprendiz.setCertificadoaptitudmedica(inscripcionPojo.getCertificadoaptitudmedica());
    */
            aprendiz.setEps(inscripcionPojo.getEps());
            aprendiz.setArl(inscripcionPojo.getArl());
            aprendiz.setSabeleerescribir(inscripcionPojo.getSabeleerescribir() == null ? "" : inscripcionPojo.getSabeleerescribir());
            aprendiz.setNiveleducativo(inscripcionPojo.getNiveleducativo() == null ? "" : inscripcionPojo.getNiveleducativo());
            aprendiz.setCargoactual(inscripcionPojo.getCargoactual() == null ? "" : inscripcionPojo.getCargoactual());
            aprendiz.setAlergias(inscripcionPojo.getAlergias() == null ? "" : inscripcionPojo.getAlergias());
            aprendiz.setMedicamentos(inscripcionPojo.getMedicamentos() == null ? "" : inscripcionPojo.getMedicamentos());
            aprendiz.setEnfermedades(inscripcionPojo.getEnfermedades() == null ? "" : inscripcionPojo.getEnfermedades());
            aprendiz.setLesiones(inscripcionPojo.getLesiones() == null ? "" : inscripcionPojo.getLesiones());
            aprendiz.setDrogas(inscripcionPojo.getDrogas() == null ? "" : inscripcionPojo.getDrogas());
            aprendiz.setNombrecontacto((inscripcionPojo.getNombrecontacto() == null ? "" : inscripcionPojo.getNombrecontacto()).toUpperCase());
            aprendiz.setTelefonocontacto(inscripcionPojo.getTelefonocontacto() == null ? "" : inscripcionPojo.getTelefonocontacto());
            aprendiz.setParentescocontacto((inscripcionPojo.getParentescocontacto() == null ? "" : inscripcionPojo.getParentescocontacto()).toUpperCase());
            aprendiz.setRegimenafiliacionseguridadsocial((inscripcionPojo.getRegimenafiliacionseguridadsocial() == null ? "" : inscripcionPojo.getRegimenafiliacionseguridadsocial()).toUpperCase());
            aprendiz.setTipovinculacionlaboral((inscripcionPojo.getTipovinculacionlaboral() == null ? "" : inscripcionPojo.getTipovinculacionlaboral()).toUpperCase());

            aprendiz.setEmbarazo(inscripcionPojo.getEmbarazo());
            aprendiz.setMesesgestacion(inscripcionPojo.getMesesgestacion());

            aprendiz.setDirecciondomicilio(inscripcionPojo.getDirecciondomicilio());
            aprendiz.setDepartamentodomicilio(inscripcionPojo.getDepartamentodomicilio());
            aprendiz.setCiudaddomicilio(inscripcionPojo.getCiudaddomicilio());
        }
    }

    private Trabajador inicializarAtributosTrabajador(TrabajadorInscripcionPojo inscripcion) {
        Trabajador trabajador = trabajadorFindByDocumentoCu.findByNumerodocumento(inscripcion.getNumerodocumento());

        if (trabajador == null) {
            trabajador = new Trabajador();
        }
        
        trabajador.setTipodocumento(inscripcion.getTipodocumento());
        trabajador.setNumerodocumento(inscripcion.getNumerodocumento());
        trabajador.setPrimernombre(StringUtil.quitarTildes(inscripcion.getPrimernombre()));
        trabajador.setSegundonombre(inscripcion.getSegundonombre());
        trabajador.setPrimerapellido(StringUtil.quitarTildes(inscripcion.getPrimerapellido()));
        trabajador.setSegundoapellido(inscripcion.getSegundoapellido());

        trabajador.setGenero(inscripcion.getGenero());
        trabajador.setFechanacimiento(inscripcion.getFechanacimiento());
        trabajador.setNacionalidad(inscripcion.getNacionalidad());
        trabajador.setTiposangre(inscripcion.getTiposangre());

        if (inscripcion.getOcupacion() != null) {
            trabajador.setOcupacion(inscripcion.getOcupacion());
        }

        if (inscripcion.getCelular() != null) {
            trabajador.setCelular(inscripcion.getCelular());
        }
        if (inscripcion.getCorreoelectronico() != null){
            trabajador.setCorreoelectronico(inscripcion.getCorreoelectronico());
        }

        return trabajador;
    }
}
