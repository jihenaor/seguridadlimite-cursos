package com.seguridadlimite.models.aprendiz.application.buscarporidgrupo;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizEvaluacionDTO;
import com.seguridadlimite.models.aprendiz.domain.AprendizEvaluacionMapper;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.asistencia.application.FindAsistenciaAprendizParaCapturaService;
import com.seguridadlimite.models.parametros.application.UpdateEvaluationDate.FindParametrosById;
import com.seguridadlimite.models.trabajador.application.EncodeFoto.ValidarExisteFotoTrabajadorService;
import com.seguridadlimite.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuscarAprendizInscripcion {
    private final AprendizEvaluacionMapper aprendizMapper;
   
    private final FindParametrosById findParametrosById;

    private final FindAsistenciaAprendizParaCapturaService consultarAsistencia;

    private final ValidarExisteFotoTrabajadorService validarExisteFotoTrabajadorService;

    private final IAprendizDao aprendizRepositorio;

    public List<AprendizEvaluacionDTO> buscar() {
        List<Aprendiz> aprendizs = aprendizRepositorio.findByFechaActivo(DateUtil.getCurrentDate());
        
        consultarAsistencia.consultarAsistencia(aprendizs);
        aprendizs.forEach(aprendiz -> {

            if ("N".equals(aprendiz.getTrabajador().getFoto())) {
                validarExisteFotoTrabajadorService.exist(aprendiz.getTrabajador());
            }
        });

        return aprendizs.stream()
                .map(aprendizMapper::toDto)
                .sorted(Comparator.<AprendizEvaluacionDTO, String>comparing(dto -> dto.getNivel().getNombre())
                        .thenComparing(AprendizEvaluacionDTO::getNombreCompletoTrabajador))
                .toList();
    }

    public List<AprendizEvaluacionDTO> buscarInscritosPorFecha(String fecha) {
        List<Aprendiz> aprendizs = aprendizRepositorio.findByFechaInscripcion(fecha);

        return aprendizs.stream()
                .map(aprendizMapper::toDto)
                .sorted(Comparator.<AprendizEvaluacionDTO, String>comparing(dto -> dto.getNivel().getNombre())
                        .thenComparing(AprendizEvaluacionDTO::getNombreCompletoTrabajador))
                .toList();
    }
}