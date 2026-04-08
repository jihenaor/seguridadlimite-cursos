package com.seguridadlimite.models.aprendiz.application.response;

import com.seguridadlimite.shared.domain.query.Response;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.dto.AprendizNoSeQueEsEstosDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public final class AprendizsResponse implements Response {
    private final List<AprendizNoSeQueEsEstosDTO> aprendizDTOS;

    public static AprendizsResponse fromAggregate(List<Aprendiz> aprendizs) {
        return new AprendizsResponse(aprendizs.stream()
                .map(aprendiz -> new AprendizNoSeQueEsEstosDTO.Builder(aprendiz.getId())
                        .build())
                .collect(Collectors.toList()));
    }
}
