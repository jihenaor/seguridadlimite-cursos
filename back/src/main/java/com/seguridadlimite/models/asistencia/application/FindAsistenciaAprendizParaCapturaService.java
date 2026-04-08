package com.seguridadlimite.models.asistencia.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.asistencia.domain.Dia;
import com.seguridadlimite.models.asistencia.domain.Modulo;
import com.seguridadlimite.models.asistencia.domain.Unidad;
import com.seguridadlimite.models.permiso.domain.PermisoFechas;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindAsistenciaAprendizParaCapturaService {

    private final RegisterAsistenciaAprendizService registerAsistenciaAprendiz;
    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;

    public void consultarAsistencia(List<Aprendiz> aprendizs) {
        procesarAsistenciaAprendiz(aprendizs);
    }

    private void procesarAsistenciaAprendiz(List<Aprendiz> aprendizs) {
        List<Asistencia> asistencias = registerAsistenciaAprendiz.find(aprendizs);

        List<PermisoTrabajoAlturas> permisoTrabajoAlturas = permisoTrabajoAlturasPort.findPermisosVigentesEnFecha(
                DateUtil.getCurrentDate());


        aprendizs.forEach(aprendiz -> {
            List<Modulo> modules = new ArrayList<>();

            List<Asistencia> asistenciaAprendiz = asistencias.stream()
                    .filter(asistencia -> Objects.equals(asistencia.getIdaprendiz(), aprendiz.getId()))
                    .toList();

            aprendiz.setAsistencias(asistenciaAprendiz);

            Optional<PermisoTrabajoAlturas> permisoOptional = permisoTrabajoAlturas.stream()
                    .filter(p -> p.getIdNivel() == aprendiz.getIdnivel())
                    .findFirst();

            PermisoTrabajoAlturas permiso = permisoOptional.orElse(null);

            List<PermisoFechas> permisoFechas = permiso == null
                    ? null
                    : permiso.getPermisoFechas();

            Set<Integer> modulosUnicos = new HashSet<>();

            for (Asistencia asistencia : aprendiz.getAsistencias()) {
                generarListadoModulos(asistencia,
                        modulosUnicos,
                        asistenciaAprendiz,
                        modules,
                        permisoFechas);
            }

            aprendiz.setModulos(modules);
        });
    }

    private void generarListadoModulos(Asistencia asistencia,
                                              Set<Integer> modulosUnicos,
                                              List<Asistencia> asistencias,
                                              List<Modulo> modules,
                                              List<PermisoFechas> permisoFechas) {
        List<Dia> dias = new ArrayList<>();
        Set<Integer> diasUnicos = new HashSet<>();

        if (!modulosUnicos.contains(asistencia.getModulo())) {
            List<Asistencia> asistenciasDelModulo = asistencias.stream()
                    .filter(asistenciaModulo -> Objects.equals(asistenciaModulo.getModulo(), asistencia.getModulo()))
                    .collect(Collectors.toList());

            for (Asistencia asistenciaModulo : asistenciasDelModulo) {
                generarListadoUnidades(asistenciaModulo, diasUnicos, asistenciasDelModulo, dias, permisoFechas);
            }

            modules.add(new Modulo(asistencia.getModulo(), dias));
        }

        modulosUnicos.add(asistencia.getModulo());
    }

    private void generarListadoUnidades(Asistencia asistenciaModulo,
                                        Set<Integer> diasUnicos,
                                        List<Asistencia> asistenciasDelModulo,
                                        List<Dia> dias,
                                        List<PermisoFechas> permisoFechas) {
        List<Unidad> unidads = new ArrayList<>();

        if (!diasUnicos.contains(asistenciaModulo.getDia())) {

            List<Asistencia> unidadesDelDia = asistenciasDelModulo.stream()
                    .filter(asistenciaDia -> Objects.equals(asistenciaDia.getDia(),
                                                                      asistenciaModulo.getDia()))
                    .collect(Collectors.toList());

            for(Asistencia asistenciaDia : unidadesDelDia) {
                asistenciaDia.setSelected(!(asistenciaDia.getFecha() == null || asistenciaDia.getFecha().isEmpty()));
                unidads.add(new Unidad(asistenciaDia.getUnidad(), asistenciaDia));
            }

            Optional<PermisoFechas> permisoFechas1 = Optional.empty();
            if (permisoFechas != null) {
                permisoFechas1 = permisoFechas.stream()
                        .filter(permisoFechas2 -> permisoFechas2.getDia() == asistenciaModulo.getDia())
                        .findFirst();
            }

            String fechaProgramada = permisoFechas1.isEmpty() ? "xx" : permisoFechas1.get().getFecha();
            dias.add(new Dia(asistenciaModulo.getDia(),
                    fechaProgramada,
                    unidads));
        }

        diasUnicos.add(asistenciaModulo.getDia());
    }
}
