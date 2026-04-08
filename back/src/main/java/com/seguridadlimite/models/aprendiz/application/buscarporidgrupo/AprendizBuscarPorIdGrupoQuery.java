package com.seguridadlimite.models.aprendiz.application.buscarporidgrupo;

import com.seguridadlimite.shared.domain.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AprendizBuscarPorIdGrupoQuery implements Query {
    private final long idgrupo;
}
