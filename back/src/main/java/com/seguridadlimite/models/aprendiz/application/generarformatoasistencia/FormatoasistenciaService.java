package com.seguridadlimite.models.aprendiz.application.generarformatoasistencia;

import com.seguridadlimite.models.aprendiz.application.extraerfirma.ExtraerFirmaServiceImpl;
import com.seguridadlimite.models.aprendiz.application.inscribiraprendiz.AsistenciaInfo;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.parametros.application.UpdateEvaluationDate.FindParametrosById;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FormatoasistenciaService {
	private final IAprendizDao dao;

	private final FindParametrosById findParametrosById;

	private final ExtraerFirmaServiceImpl extaerFirmaService;

	private List<AsistenciaInfo> fromAggregate(List<Aprendiz> aprendizs) {
		return null;
		/*
		return aprendizs.stream()
				.map(aprendiz -> {
                    return new AsistenciaInfo(
                            aprendiz.getTrabajador().getNumerodocumento(),
                            aprendiz.getGrupo().getNivel().getNombre(),
                            DateUtil.dateToString(aprendiz.getGrupo().getFechainicio(), null),
                            DateUtil.dateToString(aprendiz.getGrupo().getFechafinal(), null),
                             aprendiz.getGrupo().getEntrenador().getNombrecompleto(),
                            aprendiz.getGrupo().getSupervisor().getNombrecompleto(),
                            aprendiz.getTrabajador().getNombrecompleto(),
                            aprendiz.getIdgrupo(),
                            aprendiz.getEnfasis().getNombre(),
                            extaerFirmaService.extraerFirma(aprendiz.getId()),
                            "",
                            null
                    );
                })
				.collect(Collectors.toList());

		 */
	}
}
