package com.seguridadlimite.models.aprendiz.application.generarformatoasistencia;

import com.seguridadlimite.config.GlobalConstants;
import com.seguridadlimite.models.aprendiz.application.extraerfirma.ExtraerFirmaServiceImpl;
import com.seguridadlimite.models.aprendiz.application.inscribiraprendiz.AsistenciaInfo;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.asistencia.application.FindAsistenciaAprendizCu;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.entity.ReportePojo;
import com.seguridadlimite.models.parametros.application.UpdateEvaluationDate.FindParametrosById;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.util.IreportUtil;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class FormatoasistenciaAprendizService {

	private final IAprendizDao dao;

	private final FindParametrosById findParametrosById;

	private final ExtraerFirmaServiceImpl extaerFirmaService;

	private final FindAsistenciaAprendizCu asistenciaAprendiz;

	@Transactional(readOnly = true)
	public ReportePojo exporterAsistenciaReport(Long idaprendiz) throws IOException, JRException, BusinessException {

		if (idaprendiz == null) {
			throw new RuntimeException("Error de parametros");
		}

		Aprendiz l = dao.findById(idaprendiz).orElseThrow();

		List<Asistencia> asistencias = asistenciaAprendiz.find(idaprendiz);

		JasperReport report = IreportUtil.getJasperReport("GTH-FO-024-V02.jasper",
				"Id aprendiz: " + idaprendiz);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				fromAggregate(l, asistencias.stream().filter(asistencia -> asistencia.getFecha() != null).toList()));
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("createBy", "xx");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
		
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		String pdfBase64 = Base64.getEncoder().encodeToString(pdf);

		return new ReportePojo(pdfBase64);
	}

	private List<AsistenciaInfo> fromAggregate(Aprendiz aprendiz,
											   List<Asistencia> asistencias) throws IOException {
		List<AsistenciaInfo> asistenciaInfos = new ArrayList<>();
		String firma = extaerFirmaService.extraerFirma(aprendiz.getId());

		if (asistencias.isEmpty()) {
			throw new BusinessException("Aprendiz sin asistencias");
		}

		for(Asistencia asistencia: asistencias) {
			asistenciaInfos.add(
					AsistenciaInfo.builder()
					.numerodocumento(aprendiz.getTrabajador().getNumerodocumento())
					.nombrenivel(aprendiz.getNivel().getNombre())
					.modulo(asistencia.getModulo().toString())
					.unidad(asistencia.getUnidad().toString())
					.fecha(asistencia.getFecha())
					.formacion(asistencia.getContexto().equals("T") ? "X" : "")
					.entrenamiento(asistencia.getContexto().equals("P") ? "X" : "")
					.dia(asistencia.getDia().toString())
					.nombreentrenador("")
					.nombresupervisor("")
					.nombreaprendiz(aprendiz.getTrabajador().getNombrecompleto())
					.idgrupo(0)
					.enfasis(aprendiz.getEnfasis().getNombre())
					.firmaaprendiz(firma)
					.evaluacionconocimiento("0")
					.logoseguridad(GlobalConstants.LOGO_SEGURIDAD_LIMITE)
					.evaluacionconocimiento(aprendiz.getEteorica2() == null
							? (aprendiz.getEteorica1() == null ? "" : aprendiz.getEteorica1().toString())
							: aprendiz.getEteorica2().toString())
					.evaluacionhabilidades(aprendiz.getEpractica().toString())
					.nombresupervisor("xxx")
					.build());
		}

		return asistenciaInfos;
	}
}
