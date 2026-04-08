package com.seguridadlimite.models.personal.application;

import com.seguridadlimite.models.personal.dominio.Personal;
import com.seguridadlimite.models.personal.infraestructure.PersonalRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonalService {

	@Autowired
	private PersonalRepository dao;
	
	@Transactional(readOnly = true)
	public List<Personal> findAll() {
		return (List<Personal>) dao.findAll();
	}

	@Transactional(readOnly = true)
	public Personal findById(Long id) {
		return dao.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public List<Personal> findByTipo(String tipo) {
		if (tipo.equals("I")) {
			return dao.findInstructores();
		} else {
			return dao.findSupervisor();
		}
	}
/*
	@Override
	public Personal findByNumerodocumento(String numerodocumento) {
		return dao.findByNu(id).orElse(null);
	}
*/

	@Transactional
	public Personal save(Personal entity) {
		Personal personal;

		if (entity.getId() == null) {
			personal = new Personal();
		} else {
			personal = dao.findById(entity.getId()).orElseThrow();
		}
		return dao.save(personal);
	}

	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}

	public Personal findByNumerodocumento(String numerodocumento) {
		return dao.findBynumerodocumento(numerodocumento);
	}
	
	public Personal findByLogin(String login, String password) {
		if (password == null || password.isEmpty()) {
			return dao.findBylogin(login);
		} else {
			return dao.findBylogin(login, password);
		}
	}
	
	public String exporterReport(String reportFormat) throws FileNotFoundException, JRException {
		List<Personal> l = (List<Personal>) dao.findAll();
		String pdfBase64 = "";
		File file = ResourceUtils.getFile("classpath:personal.jrxml");
		
		JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(l);
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("createBy", "xx");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
		
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        pdfBase64 = Base64.getEncoder().encodeToString(pdf);
		/*
		switch (reportFormat) {
			case "html":
				JasperExportManager.exportReportToHtmlFile(jasperPrint, "/Users/im_a_developer/Desktop/xx.html");
				break;
			case "pdf":
				JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/im_a_developer/Desktop/xx.pdf");
				break;
			case "xx":
				
			default:
				break;
		}
		*/
		return pdfBase64;
	}
}
