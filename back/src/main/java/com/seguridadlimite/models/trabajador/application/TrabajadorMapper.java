package com.seguridadlimite.models.trabajador.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.entity.TrabajadorInscripcionPojo;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import org.springframework.stereotype.Component;

@Component
public class TrabajadorMapper {

    public TrabajadorInscripcionPojo toPojo(Trabajador trabajador,
                                            Aprendiz aprendiz,
                                            boolean existeInscripcionAbierta,
                                            boolean asistenciaCompleta,
                                            boolean aprendizContinuaAprendizaje) {
        TrabajadorInscripcionPojo pojo = new TrabajadorInscripcionPojo();
        inicializeTrabajador(trabajador, existeInscripcionAbierta, asistenciaCompleta, aprendizContinuaAprendizaje, pojo);

        if (aprendiz != null) {
            inicializeAprendiz(aprendiz, pojo);
        }

        return pojo;
    }

    private static void inicializeAprendiz(
            Aprendiz aprendiz,
            TrabajadorInscripcionPojo pojo) {
        pojo.setIdaprendiz(aprendiz.getId());
        pojo.setIdnivel(aprendiz.getIdnivel());

        pojo.setNombrenivel(aprendiz.getNivel().getNombre());
        pojo.setIdenfasis(aprendiz.getIdenfasis());
        pojo.setNombreenfasis(aprendiz.getEnfasis().getNombre());
        pojo.setDirecciondomicilio(aprendiz.getDirecciondomicilio());
        pojo.setEingreso(aprendiz.getEingreso());

        if (aprendiz.getFechainscripcion() != null) {
            String fechaEnCadena = aprendiz.getFechainscripcion();

            pojo.setFechainscripcion(fechaEnCadena);
        }
        pojo.setFechalimiteinscripcion(aprendiz.getFechalimiteinscripcion());
        pojo.setFechaencuesta(aprendiz.getFechaencuesta());
        pojo.setTieneexperienciaaltura(aprendiz.getTieneexperienciaaltura());
        pojo.setLabordesarrolla(aprendiz.getLabordesarrolla());
        pojo.setTipovinculacionlaboral(aprendiz.getTipovinculacionlaboral());
        pojo.setDepartamentodomicilio(aprendiz.getDepartamentodomicilio());
        pojo.setCiudaddomicilio(aprendiz.getCiudaddomicilio());
        pojo.setEmpresa(aprendiz.getEmpresa());
        pojo.setEps(aprendiz.getEps());
        pojo.setArl(aprendiz.getArl());
        pojo.setSabeleerescribir(aprendiz.getSabeleerescribir());
        pojo.setNiveleducativo(aprendiz.getNiveleducativo());
        pojo.setCargoactual(aprendiz.getCargoactual());
        pojo.setAlergias(aprendiz.getAlergias());
        pojo.setMedicamentos(aprendiz.getMedicamentos());
        pojo.setEnfermedades(aprendiz.getEnfermedades());
        pojo.setLesiones(aprendiz.getLesiones());
        pojo.setDrogas(aprendiz.getDrogas());
        pojo.setEmbarazo(aprendiz.getEmbarazo());
        pojo.setMesesgestacion(aprendiz.getMesesgestacion());

        pojo.setNombrecontacto(aprendiz.getNombrecontacto());
        pojo.setTelefonocontacto(aprendiz.getTelefonocontacto());
        pojo.setParentescocontacto(aprendiz.getParentescocontacto());
        pojo.setFechaUltimaAsistencia(aprendiz.getFechaUltimaAsistencia());
        pojo.setRegimenafiliacionseguridadsocial(aprendiz.getRegimenafiliacionseguridadsocial());

    }

    private static void inicializeTrabajador(Trabajador trabajador,
                                             boolean existeInscripcionAbierta,
                                             boolean asistenciaCompleta,
                                             boolean aprendizContinuaAprendizaje, TrabajadorInscripcionPojo pojo) {
        pojo.setId(trabajador.getId());
        pojo.setTipodocumento(trabajador.getTipodocumento());
        pojo.setNumerodocumento(trabajador.getNumerodocumento());
        pojo.setPrimernombre(trabajador.getPrimernombre());
        pojo.setSegundonombre(trabajador.getSegundonombre());
        pojo.setPrimerapellido(trabajador.getPrimerapellido());
        pojo.setSegundoapellido(trabajador.getSegundoapellido());
        pojo.setGenero(trabajador.getGenero());
        pojo.setNacionalidad(trabajador.getNacionalidad());
        pojo.setTiposangre(trabajador.getTiposangre());
        pojo.setOcupacion(trabajador.getOcupacion());
        pojo.setCelular(trabajador.getCelular());
        pojo.setCorreoelectronico(trabajador.getCorreoelectronico());
        pojo.setExisteinscripcionabierta(existeInscripcionAbierta);
        pojo.setAsistenciacompleta(asistenciaCompleta);
        pojo.setAprendizContinuaAprendizaje(aprendizContinuaAprendizaje);
        pojo.setFechanacimiento(trabajador.getFechanacimiento());
    }
}
