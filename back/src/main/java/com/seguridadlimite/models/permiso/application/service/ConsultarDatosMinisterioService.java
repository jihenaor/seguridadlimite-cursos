package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.permiso.application.port.in.ConsultarDatosMinisterioUseCase;
import com.seguridadlimite.models.permiso.application.port.out.ConsultarDatosMinisterioPort;
import com.seguridadlimite.models.permiso.domain.InformeministerioDto;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarDatosMinisterioService implements ConsultarDatosMinisterioUseCase {

    private final ConsultarDatosMinisterioPort consultarDatosMinisterioPort;

    @Override
    public List<InformeministerioDto> ejecutar(List<Integer> idPermisosTrabajos) {
        List<InformeministerioDto> informesMinisterio = new ArrayList<>();
        
        // Buscar permisos de trabajo por IDs
        List<PermisoTrabajoAlturas> permisos = consultarDatosMinisterioPort.buscarPermisosPorIds(idPermisosTrabajos);
        
        for (PermisoTrabajoAlturas permiso : permisos) {
            // Buscar aprendices asociados al permiso
            List<Aprendiz> aprendices = consultarDatosMinisterioPort.buscarAprendicesPorIdPermiso(permiso.getIdPermiso());
            
            // Crear un informe por cada aprendiz del permiso
            for (Aprendiz aprendiz : aprendices) {
                InformeministerioDto informe = mapearAInformeMinisterio(permiso, aprendiz);
                informesMinisterio.add(informe);
            }
        }
        
        return informesMinisterio;
    }
    
    private InformeministerioDto mapearAInformeMinisterio(PermisoTrabajoAlturas permiso, Aprendiz aprendiz) {
        InformeministerioDto informe = new InformeministerioDto();
        
        // Datos del permiso
        informe.setId(permiso.getIdPermiso().longValue());
        informe.setFechainicio(permiso.getFechaInicio());
        informe.setFechafinal(permiso.getValidohasta());
        informe.setCupoinicial(permiso.getCupoinicial());
        informe.setCupofinal(permiso.getCupofinal());
        informe.setCodigoministerio(permiso.getCodigoministerio());
        
        // Datos del entrenador (personaautoriza1)
        if (permiso.getPersonaautoriza1() != null) {
            informe.setEntrenadorId(permiso.getPersonaautoriza1().getId().longValue());
            informe.setEntrenadorNombrecompleto(permiso.getPersonaautoriza1().getNombrecompleto());
        }
        
        // Datos del supervisor (personaautoriza2)
        if (permiso.getPersonaautoriza2() != null) {
            informe.setSupervisorId(permiso.getPersonaautoriza2().getId().longValue());
            informe.setSupervisorNombrecompleto(permiso.getPersonaautoriza2().getNombrecompleto());
        }
        
        // Datos del aprendiz
        informe.setAprendizId(aprendiz.getId());
        informe.setAprendizCodigoverificacion(aprendiz.getCodigoverificacion());
        
        // Datos del trabajador
        Trabajador trabajador = aprendiz.getTrabajador();
        if (trabajador != null) {
            informe.setTrabajadorId(trabajador.getId());
            informe.setTrabajadorNumerodocumento(trabajador.getNumerodocumento());
            informe.setTrabajadorNombrecompleto(trabajador.getNombrecompleto());
            informe.setTrabajadorAreatrabajo(trabajador.getOcupacion());
            informe.setTrabajadorNiveleducativo(aprendiz.getNiveleducativo());
            informe.setTrabajadorCargoactual(aprendiz.getCargoactual());
            informe.setTrabajadorGenero(trabajador.getGenero());
            informe.setTrabajadorNacionalidad(trabajador.getNacionalidad());
        }
        
        return informe;
    }
}
