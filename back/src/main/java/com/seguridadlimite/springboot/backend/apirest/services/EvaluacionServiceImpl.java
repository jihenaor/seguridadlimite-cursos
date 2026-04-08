package com.seguridadlimite.springboot.backend.apirest.services;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.EvaluacionPojo;
import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
@AllArgsConstructor
public class EvaluacionServiceImpl {
	private final IEvaluacionDao dao;
	private final IPreguntaDao preguntaDao;
	private final IAprendizDao aprendizDao;
	private final IAprendizDao aprendizRDao;

	@Transactional
	public EvaluacionPojo findAndRegister(
			String tipoevaluacion,
			Long idaprendiz, 
			Integer numeroevaluacion,
			Long idnivel) {
	    EvaluacionPojo e = new EvaluacionPojo();

        String sabeleerescribir = aprendizDao.consultarAprendizSabeLeerEscribir(idaprendiz);

        List<Pregunta> preguntas = preguntaDao.findByNiveltipoevaluacionRandom(
                idnivel,
				tipoevaluacion,
                (sabeleerescribir == null ? "N" : sabeleerescribir).equals("S") ? "S" : "N");
        List<Evaluacion> l = dao.findEvaluacionAprendiz(tipoevaluacion,
				idaprendiz, 
				numeroevaluacion);
		
		if (l.isEmpty()) {
			l = new ArrayList<>();
			for (Pregunta pregunta : preguntas) {
				creevaluacion(idaprendiz, l, pregunta, numeroevaluacion);
			}
			dao.saveAll(l);
		}
		for (Pregunta pregunta : preguntas) {

			for(Evaluacion ev : l) {
				if (Objects.equals(ev.getIdpregunta(), pregunta.getId())) {
					pregunta.setIdevaluacion(ev.getId());
				}
			}
		}

		e.setEvaluacions(l);
		e.setPreguntas(preguntas);
		
		return e;
	}

	@Transactional
	public List<Pregunta> findEvaluacionTeoricaIdaprendiz(Long idaprendiz) {
		Aprendiz aprendiz = aprendizDao.findById(idaprendiz).orElse(null);
		
		return this.findAndRegister("T",
				idaprendiz,
				1,
				aprendiz.getIdnivel()).getPreguntas();
	}

	private static void creevaluacion(Long idaprendiz,
									  List<Evaluacion> l,
									  Pregunta pregunta,
									  int numeroEvaluacion) {
		Evaluacion evaluacion = new Evaluacion();

		evaluacion.setIdaprendiz(idaprendiz);
		evaluacion.setIdpregunta(pregunta.getId());
		evaluacion.setPregunta(pregunta);
		evaluacion.setRespuestacorrecta("X");
		evaluacion.setNumero(numeroEvaluacion);

		evaluacion.setCreateAt(new Date());
		evaluacion.setUpdateAt(new Date());

		l.add(evaluacion);
	}

	@Transactional
	public List<Pregunta> findEvaluacionPracticaMovilIdaprendiz(Long idaprendiz) {
		Aprendiz aprendiz = aprendizDao.findById(idaprendiz)
				.orElseThrow(() -> new RuntimeException(String.format("El aprendiz es null buscando la evaluacion practica para el id: %s", idaprendiz)));

		List<Pregunta> preguntas = preguntaDao.findByNiveltipoevaluacionOrden(aprendiz.getIdnivel(), "P");

		if (preguntas == null || preguntas.isEmpty()) {
			throw new RuntimeException(String.format("No existen preguntas para la evaluacion practica en el nivel %s", aprendiz.getIdnivel()));
		}

		List<Evaluacion> l = dao.findEvaluacionPractica("P", idaprendiz);
		
		if (l.isEmpty()) {
			l = new ArrayList<>();
			for (Pregunta pregunta : preguntas) {
				creevaluacion(idaprendiz, l, pregunta, 0);
			}
			dao.saveAll(l);
		}

		for (Pregunta pregunta : preguntas) {
			for (Evaluacion e : l) {
				if (e.getPregunta().getId() == pregunta.getId()) {
					pregunta.setIdevaluacion(e.getId());
					pregunta.setRespuestacorrecta(e.getRespuestacorrecta());
				}
			}
		}
		
		return preguntas;
	}

	@Transactional(readOnly = true)
	public Evaluacion findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Transactional
	public void save(List<Evaluacion> evaluacions) {
			dao.saveAll(evaluacions);
	}

	@Transactional
	public void saveevaluacion(List<Pregunta> entity, String tipoEvaluacion) {
		Long idaprendiz = null;
		Double nota;
		Aprendiz aprendiz = null;
		
		int caprobadas = 0;
		int contador = 0;
		
		for (Pregunta p : entity) {
			contador++;
			if (p.getIdevaluacion() == null) {
				throw new RuntimeException("Error una pregunta sin id evaluacion");
			} else {
				Evaluacion e = dao.findById(p.getIdevaluacion()).orElse(null);
				
				if (e != null) {
					if (contador == 1) {
						idaprendiz = e.getIdaprendiz();
						aprendiz = aprendizDao.findById(idaprendiz).orElse(null);
					}
					
					e.setRespuestacorrecta(p.getRespuestacorrecta());
					e.setNumerorespuesta(p.getNumerorespuesta());

					if (p.getRespuestacorrecta().equals("S")) {
						caprobadas++;
					}
					
					dao.save(e);	
				}
			}
		}

		if (idaprendiz == null || aprendiz == null) {
			throw new RuntimeException("No se ha seleccionado el aprendiz");
		}

		nota = ((double) caprobadas / contador) * 5;

		try {
			if (tipoEvaluacion.equals("T")) {

				if (aprendiz.getEteorica1() == 0) {
					aprendizDao.updateEvaluacionteorica1(nota, idaprendiz);
				} else {
					aprendizDao.updateEvaluacionteorica2(nota, idaprendiz);
				}
			} else {
				aprendizDao.updateEvaluacionpractica(nota, idaprendiz);
			}

		} catch (Exception e) {
			throw new RuntimeException("Error actualizando nota " + nota + " del aprendiz " + idaprendiz + " " + e.getMessage());
		}
	}
	
	@Transactional
	public void deleteAll() {
		dao.deleteAll();
	}

	private static class Result {
		public final int caprobadas;
		public final int contador;

		public Result(int caprobadas, int contador) {
			this.caprobadas = caprobadas;
			this.contador = contador;
		}
	}

	@Transactional
	public void update(List<Evaluacion> entity) {
		Double nota;
		int caprobadas = 0;
		int numeroevaluacion;
		Long idaprendiz;
		
		idaprendiz = entity.get(0).getIdaprendiz();
		numeroevaluacion = entity.get(0).getNumero();

		for (Evaluacion e : entity) {
			Evaluacion evaluacion = dao.findById(e.getId()).orElse(null);

			if (evaluacion != null) {
				evaluacion.setNumerorespuesta(e.getNumerorespuesta());
				evaluacion.setRespuestacorrecta(e.getRespuestacorrecta());
				if (evaluacion.getRespuestacorrecta().equals("S")) {
					caprobadas++;
				}

				dao.save(evaluacion);
			}
		}
		nota = ((double)caprobadas / entity.size()) * 5;
		
		if (numeroevaluacion == 1) {
			aprendizDao.updateEvaluacionteorica1(nota, idaprendiz);
		} else {
			aprendizDao.updateEvaluacionteorica2(nota, idaprendiz);
		}		
	}
	
	public String exporterFormatoevaluacionpracticaReport(Long idaprendiz) throws FileNotFoundException, JRException {
		List<Evaluacion> l = dao.findEvaluacionPractica("P", idaprendiz);
		Aprendiz aprendiz;
		
		aprendiz = aprendizRDao.findById(idaprendiz).orElse(null);

		for (Evaluacion evaluacionR : l) {
			evaluacionR.setAprendiz(aprendiz);
		}
		
		String pdfBase64 = "";
		File file = ResourceUtils.getFile("classpath:evaluacionpractica.jrxml");
		
		JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(l);
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("createBy", "xx");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
		
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        pdfBase64 = Base64.getEncoder().encodeToString(pdf);

		return pdfBase64 == null ? "Sin datos" : pdfBase64;
	}

	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
