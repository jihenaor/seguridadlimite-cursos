package com.seguridadlimite.models.aprendiz.application.buscarporidgrupo;

import com.seguridadlimite.shared.domain.query.QueryHandler;
import com.seguridadlimite.models.aprendiz.application.response.AprendizsResponse;
import org.springframework.stereotype.Service;


@Service
    public class AprendizBuscarPorIdGrupoHandler implements QueryHandler<AprendizBuscarPorIdGrupoQuery, AprendizsResponse> {
    private final FindAprendizByIdGrupo embargoAlertaBuscarTipo;

    public AprendizBuscarPorIdGrupoHandler(FindAprendizByIdGrupo embargoBuscarRadicado) {
        this.embargoAlertaBuscarTipo = embargoBuscarRadicado;
    }

    @Override
    public AprendizsResponse handle(AprendizBuscarPorIdGrupoQuery query) {
        return AprendizsResponse.fromAggregate(embargoAlertaBuscarTipo.buscar(query.getIdgrupo()));
    }
}

