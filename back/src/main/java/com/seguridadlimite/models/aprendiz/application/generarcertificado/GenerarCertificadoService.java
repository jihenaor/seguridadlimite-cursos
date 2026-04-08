package com.seguridadlimite.models.aprendiz.application.generarcertificado;

import com.seguridadlimite.models.aprendiz.application.inscribiraprendiz.CertificadoInfo;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.entity.ReportePojo;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.util.IreportUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.*;

@Service
public class GenerarCertificadoService {

	@Autowired
	private IAprendizDao dao;

	public ReportePojo generar(Long idaprendiz) throws FileNotFoundException, JRException, BusinessException {
		Aprendiz aprendiz = dao.findById(idaprendiz).orElseThrow();

		JasperReport report = IreportUtil.getJasperReport("certificado.jrxml",
				"Id aprendiz: " + idaprendiz);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fromAggregate(aprendiz));
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("createBy", "xx");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
		
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		String pdfBase64 = Base64.getEncoder().encodeToString(pdf);

		return new ReportePojo(pdfBase64);
	}

	private List<CertificadoInfo> fromAggregate(Aprendiz aprendiz) {
		List<CertificadoInfo> certificadoInfos = new ArrayList<>();
		String base64Image;

		base64Image = "./certificado-" + aprendiz.getId() + ".png";

		certificadoInfos.add(
			new CertificadoInfo(
				aprendiz.getTrabajador().getNombrecompleto(),
				aprendiz.getTrabajador().getNumerodocumento(),
				aprendiz.getArl(),
				aprendiz.getNivel().getNombre(),
				aprendiz.getEmpresa() == null ? "" : aprendiz.getEmpresa(),
				aprendiz.getNit(),
				aprendiz.getRepresentantelegal(),
				aprendiz.getFechaemision(),
				aprendiz.getDuraciontotal() + "",
				"codigoministerio",
				aprendiz.getCodigoverificacion(),
				base64Image,
				"2020-01-01"
			));

		return certificadoInfos;
	}
}
