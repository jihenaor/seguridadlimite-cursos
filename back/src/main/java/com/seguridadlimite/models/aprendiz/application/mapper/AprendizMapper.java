package com.seguridadlimite.models.aprendiz.application.mapper;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizDTO;
import com.seguridadlimite.models.aprendiz.domain.TrabajadorDTO;
import com.seguridadlimite.models.nivel.domain.NivelDTO;
import org.springframework.stereotype.Component;

@Component
public class AprendizMapper {
    public AprendizDTO toDTO(Aprendiz aprendiz) {

        NivelDTO nivel = NivelDTO.builder()
                .id(aprendiz.getNivel().getId())
                .nombre(aprendiz.getNivel().getNombre())
                .build();

        TrabajadorDTO trabajadorDTO = TrabajadorDTO
                .builder()
                .id(aprendiz.getTrabajador().getId())
                .tipodocumento(aprendiz.getTrabajador().getTipodocumento())
                .documento(aprendiz.getTrabajador().getNumerodocumento())
                .primernombre(aprendiz.getTrabajador().getPrimernombre())
                .segundonombre(aprendiz.getTrabajador().getSegundonombre())
                .primerapellido(aprendiz.getTrabajador().getPrimerapellido())
                .segundoapellido(aprendiz.getTrabajador().getSegundoapellido())
                .genero(aprendiz.getTrabajador().getGenero())
                .paisnacimiento(aprendiz.getTrabajador().getNacionalidad())
                .fechanacimiento(aprendiz.getTrabajador().getFechanacimiento())
                .build();

        return AprendizDTO.builder()
                .id(aprendiz.getId())
                .nombrecompleto(aprendiz.getTrabajador().getNombrecompleto())
                .trabajador(trabajadorDTO)
                .celular(aprendiz.getTrabajador().getCelular())
                .correoelectronico(aprendiz.getTrabajador().getCorreoelectronico())
                .idPermiso(aprendiz.getIdPermiso())
                .fechainscripcion(aprendiz.getFechainscripcion())
                .asistenciaCompleta(aprendiz.isAsistenciaCompleta())
                .niveleducativo(aprendiz.getNiveleducativo())
                .areatrabajo(aprendiz.getEnfasis().getNombre())
                .cargoactual(aprendiz.getCargoactual())
                .nivel(nivel)
                .empleador(aprendiz.getEmpresa())
                .arl(aprendiz.getArl())
                .build();
    }
}