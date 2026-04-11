package com.seguridadlimite.models.aprendiz.application.informpagospendientes;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.Pagopendienteempresa;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneratePendingPaymentReport {
    private final IAprendizDao dao;

    public List<Pagopendienteempresa> find() {
        List<Aprendiz> aprendices = dao.findAprendicesPendientesPago();

        Map<String, List<Aprendiz>> aprendicesAgrupados = aprendices.stream()
                .collect(Collectors.groupingBy(
                        aprendiz -> (aprendiz.getNit() == null ? "" : aprendiz.getNit())
                                + (aprendiz.getEmpresa() == null ? "" : aprendiz.getEmpresa())
                                + (aprendiz.getFechainscripcion() == null ? "" : aprendiz.getFechainscripcion())
                ));

        return aprendicesAgrupados.values().stream()
                .map(aprendiz -> new Pagopendienteempresa(
                        aprendiz.get(0).getEmpresa(),
                        aprendiz.get(0).getNit(),
                        DateUtil.stringToLocalDate(aprendiz.get(0).getFechainscripcion()),
                        aprendiz
                ))
                .collect(Collectors.toList());
    }
}
