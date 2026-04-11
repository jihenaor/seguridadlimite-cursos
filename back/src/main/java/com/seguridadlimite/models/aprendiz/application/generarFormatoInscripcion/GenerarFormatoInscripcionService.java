package com.seguridadlimite.models.aprendiz.application.generarFormatoInscripcion;

import com.seguridadlimite.config.GlobalConstants;
import com.seguridadlimite.models.aprendiz.application.extraerfirma.ExtraerFirmaServiceImpl;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.enfasis.domain.Enfasis;
import com.seguridadlimite.models.enfasis.domain.EnfasisEnum;
import com.seguridadlimite.models.entity.*;
import com.seguridadlimite.models.evaluacion.dominio.Evaluacion;
import com.seguridadlimite.models.grupopregunta.domain.Grupopregunta;
import com.seguridadlimite.models.grupopregunta.infraestructure.IGrupopreguntaDao;
import com.seguridadlimite.models.nivel.domain.NivelEnum;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import com.seguridadlimite.models.quiz.application.ConsultarQuizAprendizService;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.services.EnfasisServiceImpl;
import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import com.seguridadlimite.util.IreportUtil;
import com.seguridadlimite.util.ItextMerge;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@AllArgsConstructor
public class GenerarFormatoInscripcionService {
	private final GetPathFiles getPathFiles;

	private final IAprendizDao aprendizDao;

	private final IGrupopreguntaDao grupoDao;

	private final IPreguntaDao preguntaDao;

	private final IEvaluacionDao evaluacionDao;

	private final EnfasisServiceImpl enfasisService;

	private final ExtraerFirmaServiceImpl extaerFirmaService;

	private final ItextMerge itextMerge;

	private final ConsultarQuizAprendizService consultarQuizAprendizService;


	@Transactional(readOnly = true)
	public Aprendiz findById(Long id) {
		return aprendizDao.findById(AprendizId.toInteger(id)).orElse(null);
	}

	private Formatoinscripcion generarFormatoInscripcionPojo(
			Aprendiz t,
			List<Enfasis> enfasisList,
			List<Pregunta> preguntas)  {
		Formatoinscripcion formatoinscripcion = new Formatoinscripcion();

		formatoinscripcion.setLogoseguridad(GlobalConstants.LOGO_SEGURIDAD_LIMITE);
		formatoinscripcion.setTipodocumento(t.getTrabajador().getTipodocumento());
		formatoinscripcion.setNumerodocumento(t.getTrabajador().getNumerodocumento());
		formatoinscripcion.setPrimernombre(t.getTrabajador().getPrimernombre());
		formatoinscripcion.setSegundonombre(t.getTrabajador().getSegundonombre());
		formatoinscripcion.setPrimerapellido(t.getTrabajador().getPrimerapellido());
		formatoinscripcion.setSegundoapellido(t.getTrabajador().getSegundoapellido());
		formatoinscripcion.setNombrecompleto(t.getTrabajador().getPrimernombre() + " "
				+ (t.getTrabajador().getSegundonombre() == null ? "" : t.getTrabajador().getSegundonombre())+ " "
				+ t.getTrabajador().getPrimerapellido() + " "
				+ (t.getTrabajador().getSegundoapellido() == null ? "" : t.getTrabajador().getSegundoapellido()));

		formatoinscripcion.setCodigoverificacion(t.getCodigoverificacion());
		formatoinscripcion.setNiveleducativoprimaria(t.getNiveleducativo().equals("1") ? "X" : "");
		formatoinscripcion.setNiveleducativobachillerato(t.getNiveleducativo().equals("2") ? "X" : "");
		formatoinscripcion.setNiveleducativotecnico(t.getNiveleducativo().equals("3") ? "X" : "");
		formatoinscripcion.setNiveleducativotecnologo(t.getNiveleducativo().equals("4") ? "X" : "");
		formatoinscripcion.setNiveleducativouniversitaria(t.getNiveleducativo().equals("5") ? "X" : "");
				
		formatoinscripcion.setCargoactual(t.getCargoactual());
		formatoinscripcion.setGenero(t.getTrabajador().getGenero());
		formatoinscripcion.setNacionalidad(t.getTrabajador().getNacionalidad());
		formatoinscripcion.setTiposangre(t.getTrabajador().getTiposangre());
		formatoinscripcion.setOcupacion(t.getTrabajador().getOcupacion());

		formatoinscripcion.setDepartamentodomicilio(t.getDepartamentodomicilio());
		formatoinscripcion.setCiudaddomicilio(t.getCiudaddomicilio());

		formatoinscripcion.setDirecciondomicilio(t.getDirecciondomicilio());
		formatoinscripcion.setCelular(t.getTrabajador().getCelular());
		formatoinscripcion.setCorreoelectronico(t.getTrabajador().getCorreoelectronico());
		formatoinscripcion.setEps(t.getEps());
		formatoinscripcion.setArl(t.getArl());
		formatoinscripcion.setSabeleerescribir(t.getSabeleerescribir());

		Pattern pattern = Pattern.compile("(HSE|SST|SUPERVISOR|INSPECTOR)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(t.getLabordesarrolla());
		if (matcher.find()) {
			formatoinscripcion.setLabordesarrolla(t.getLabordesarrolla());
			formatoinscripcion.setOtralabor("");
		} else {
			formatoinscripcion.setLabordesarrolla("");
			formatoinscripcion.setOtralabor(t.getLabordesarrolla());
		}

		formatoinscripcion.setAlergias(t.getAlergias() == null ? "" : t.getAlergias());
		formatoinscripcion.setMedicamentos(t.getMedicamentos() == null ? "" : t.getMedicamentos());
		formatoinscripcion.setEnfermedades(t.getEnfermedades() == null ? "" : t.getEnfermedades());
		
		formatoinscripcion.setLesiones(t.getLesiones() == null ? "" : t.getLesiones());
		formatoinscripcion.setDrogas(t.getDrogas() == null ? "" : t.getDrogas());

		formatoinscripcion.setNombrecontacto(t.getNombrecontacto());
		formatoinscripcion.setTelefonocontacto(t.getTelefonocontacto());
		formatoinscripcion.setParentescocontacto(t.getParentescocontacto());
		
		formatoinscripcion.setTrabajadorautorizado(t.getIdnivel() == NivelEnum.TRABAJADOR_AUTORIZADO.getCodigo() ? "X" : "");
		formatoinscripcion.setNivelreentrenamiento(t.getIdnivel() == NivelEnum.REENTRENAMIENTO.getCodigo() ? "X" : "");
		formatoinscripcion.setNivelcoordinador(t.getIdnivel() == NivelEnum.COORDINADOR.getCodigo() ? "X" : "");
		formatoinscripcion.setNivelbasicooperativo(t.getIdnivel() == NivelEnum.BASICO_OPERATIVO.getCodigo() ? "X" : "");
		formatoinscripcion.setActualizacioncoordinador(t.getIdnivel() == NivelEnum.ACTUALIZACION_COORDINADOR.getCodigo() ? "X" : "");
		formatoinscripcion.setAdministrativojefearea(t.getIdnivel() == NivelEnum.JEFE_DE_AREA.getCodigo() ? "X" : "");

		if (t.getIdenfasis() != null && t.getIdenfasis() >= 1 && t.getIdenfasis() <= 4) {
			String[] enfasisValues = new String[]{
					t.getIdenfasis() == EnfasisEnum.HIDROCARBUROS.getCodigo() ? "X" : "",
					t.getIdenfasis() == EnfasisEnum.TELECOMUNICACIONES.getCodigo() ? "X" : "",
					t.getIdenfasis() == EnfasisEnum.CONSTRUCCION.getCodigo() ? "X" : "",
					t.getIdenfasis() == EnfasisEnum.ELECTRICO.getCodigo() ? "X" : ""
			};
			// Seteamos los valores de enfasis
			formatoinscripcion.setHidrocarburos(enfasisValues[0]);
			formatoinscripcion.setTelecomunicaciones(enfasisValues[1]);
			formatoinscripcion.setConstruccion(enfasisValues[2]);
			formatoinscripcion.setElectrico(enfasisValues[3]);

			formatoinscripcion.setOtros("");
		} else {
			// Si el id de enfasis no está entre 1 y 4, seteamos todos los valores de enfasis como vacíos
			formatoinscripcion.setHidrocarburos("");
			formatoinscripcion.setTelecomunicaciones("");
			formatoinscripcion.setConstruccion("");
			formatoinscripcion.setElectrico("");
			// Obtener el nombre del enfasis correspondiente al id de enfasis
			formatoinscripcion.setOtros(EnfasisEnum.getNombrePorCodigo(enfasisList,
					t.getIdenfasis() != null ? t.getIdenfasis() : 0));
		}

		formatoinscripcion.setEmbarazo(t.getEmbarazo());
		formatoinscripcion.setMesesgestacion(t.getMesesgestacion());

		Calendar calendario = Calendar.getInstance();
		calendario.setTime(t.getCreateAt());

		formatoinscripcion.setDia(calendario.get(Calendar.DAY_OF_MONTH) + "");
		formatoinscripcion.setMes((calendario.get(Calendar.MONTH) + 1) + "");
		formatoinscripcion.setAno(calendario.get(Calendar.YEAR) + "");

		formatoinscripcion.setFirmaaprendiz(extaerFirmaService.extraerFirma(AprendizId.toLong(t.getId())));
		formatoinscripcion.setEingresoexcelente(t.getEingreso() >= 4 ? "X" : "");
		formatoinscripcion.setEingresobueno(t.getEingreso() <= 3 ? "X" : "");

		formatoinscripcion.setDocumentoidentidad(t.getDocumentoidentidad() == null || t.getDocumentoidentidad().equals("N") ? "" : "X");
		formatoinscripcion.setUltimopagoseguridadsocial(t.getUltimopagoseguridadsocial() == null || t.getUltimopagoseguridadsocial().equals("N") ? "" : "X");
		formatoinscripcion.setAfiliacionseguridadsocial(t.getAfiliacionseguridadsocial() == null || t.getAfiliacionseguridadsocial().equals("N") ? "" : "X");
		formatoinscripcion.setCertificadoaptitudmedica(t.getCertificadoaptitudmedica() == null || t.getCertificadoaptitudmedica().equals("N") ? "" : "X");

		if (t.getTipovinculacionlaboral() == null) {
			formatoinscripcion.setTipovinculacionlaboraldependiente("");
			formatoinscripcion.setTipovinculacionlaboralindependiente("");
			formatoinscripcion.setTipovinculacionlaboralninguno("");
		} else {
			formatoinscripcion.setTipovinculacionlaboraldependiente(t.getTipovinculacionlaboral().equals("D") ? "X" : "");
			formatoinscripcion.setTipovinculacionlaboralindependiente(t.getTipovinculacionlaboral().equals("I") ? "X" : "");
			formatoinscripcion.setTipovinculacionlaboralninguno(t.getTipovinculacionlaboral().equals("N") ? "X" : "");
		}

		if (t.getRegimenafiliacionseguridadsocial() == null) {
			formatoinscripcion.setRegimenafiliacionseguridadsocialcontributivo("");
			formatoinscripcion.setRegimenafiliacionseguridadsocialsubsidiado("");
			formatoinscripcion.setRegimenafiliacionseguridadsocialotro("");
		} else {
			formatoinscripcion.setRegimenafiliacionseguridadsocialcontributivo(
					t.getRegimenafiliacionseguridadsocial().equals("C") ? "X" : "" );
			formatoinscripcion.setRegimenafiliacionseguridadsocialsubsidiado(
					t.getRegimenafiliacionseguridadsocial().equals("S") ? "X" : "" );
			formatoinscripcion.setRegimenafiliacionseguridadsocialotro(
					t.getRegimenafiliacionseguridadsocial().equals("O") ? "X" : "" );
		}

		formatoinscripcion.setCertificadotrabajadorautorizado(t.getCertificadotrabajadorautorizado() == null || t.getCertificadotrabajadorautorizado().equals("N") ? "" : "X");
		formatoinscripcion.setCertificadonivelcoordinador(t.getCertificadonivelcoordinador() == null || t.getCertificadonivelcoordinador().equals("N") ? "" : "X");

		formatoinscripcion.setEmpresa(t.getEmpresa());
		formatoinscripcion.setConceptoapto("");
		formatoinscripcion.setCertificadovigente("");

		formatoinscripcion.setRegimenafiliacionseguridadsocialcontributivo(t.getRegimenafiliacionseguridadsocial().equals("C") ? "X" : "");
		formatoinscripcion.setRegimenafiliacionseguridadsocialsubsidiado(t.getRegimenafiliacionseguridadsocial().equals("S") ? "X" : "");
		formatoinscripcion.setRegimenafiliacionseguridadsocialotro(t.getRegimenafiliacionseguridadsocial().equals("O") ? "X" : "");

		formatoinscripcion.setCopiadocumentoidentidad("S".equals(t.getDocumentoidentidad()) ? "X" : "");
		formatoinscripcion.setCopiapagoseguridadsocial("S".equals(t.getUltimopagoseguridadsocial()) ? "X" : "");
		formatoinscripcion.setAfiliciacionseguridadsocial("S".equals(t.getAfiliacionseguridadsocial()) ? "X" : "");
		formatoinscripcion.setCertificadoaptitudmedica("S".equals(t.getCertificadoaptitudmedica()) ? "X" : "");

		formatoinscripcion.setReentrenamiento(t.getCertificadotrabajadorautorizado() == null || t.getCertificadotrabajadorautorizado().equals("N") ? "" : "X");
		formatoinscripcion.setActualizadocoordinador(t.getCertificadonivelcoordinador() == null || t.getCertificadonivelcoordinador().equals("N") ? "" : "X");

//		formatoinscripcion.setSupervisor(t.getGrupo().getSupervisor().getNombrecompleto());
//		formatoinscripcion.setEntrenadoracargo(t.getGrupo().getEntrenador().getNombrecompleto());
//		formatoinscripcion.setPersonadeapoyo(t.getGrupo().getPersonaapoyo() == null ? "" : t.getGrupo().getPersonaapoyo().getNombrecompleto());

		formatoinscripcion.setEvaluacionconocimientopregunta1(generarStringEvaluacionConocimiento(preguntas, 1));
		formatoinscripcion.setEvaluacionconocimientopregunta2(generarStringEvaluacionConocimiento(preguntas, 2));
		formatoinscripcion.setEvaluacionconocimientopregunta3(generarStringEvaluacionConocimiento(preguntas, 3));
		formatoinscripcion.setEvaluacionconocimientopregunta4(generarStringEvaluacionConocimiento(preguntas, 4));
		formatoinscripcion.setEvaluacionconocimientopregunta5(generarStringEvaluacionConocimiento(preguntas, 5));

		return formatoinscripcion;
	}

	private String generarStringEvaluacionConocimiento(List<Pregunta> preguntas, int orden){
		Pregunta p = preguntas.stream()
				.filter(pregunta -> pregunta.getIdgrupo() == 0 && pregunta.getOrden() == orden)
				.toList().get(0);

		String texto = p.getOrden() + ". " + p.getPregunta() + "<br />";

		for (Respuesta respuesta : p.getRespuestas()) {
			texto += respuesta.getNumero() + ") " + respuesta.getRespuesta();
			if (respuesta.getNumero() == p.getNumerorespuesta()) {
				texto += " (X)  " + ("S".equals(p.getRespuestacorrecta()) ? "Correcto" : "Incorrecto");
			}
			texto += "<br />";
		}

		return texto;
	}

	private byte[] generarBytesFormatoinscripcionReport(Aprendiz t, List<Pregunta> preguntas) {
		List<Enfasis> enfasisList = enfasisService.findAll();
		List<Formatoinscripcion> l = new ArrayList<>();

		l.add(generarFormatoInscripcionPojo(t, enfasisList, preguntas));

		JasperReport report = IreportUtil.getJasperReport("GTH-FO-020-V004.jasper",
				t.getTrabajador().getNumerodocumento());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(l);
		Map<String, Object> parameters = new HashMap<>();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			log.error("Se presento un error generando el reporte ", e);
			throw new RuntimeException("Error generando el reporte");
		}
	}

	public String exporterFormatoinscripcionReport(Long idaprendiz)  {
		Aprendiz t = findById(idaprendiz);
		String tipoevaluacion = "I";
		int numero = 0;

		if (t == null) {
			return "No existe trabajador ";
		}

		List<Pregunta> preguntas = consultarQuizAprendizService.findPreguntasAprendiz(
				idaprendiz,
				tipoevaluacion,
				numero);

		byte[] pdf = generarBytesFormatoinscripcionReport(t, preguntas);

		String pdfBase64 = Base64.getEncoder().encodeToString(pdf);

		return pdfBase64 == null ? "Sin datos" : pdfBase64;
	}
	
	private Perfilingreso generarPerfilingresoPojo(Aprendiz t)  {
		Perfilingreso f = new Perfilingreso();

		f.setTipodocumento(t.getTrabajador().getTipodocumento());
		f.setNumerodocumento(t.getTrabajador().getNumerodocumento());
		f.setNombrecompleto(t.getTrabajador().getNombrecompleto());

		f.setTrabajadorautorizado(t.getIdnivel() == NivelEnum.TRABAJADOR_AUTORIZADO.getCodigo() ? "X" : "");
		f.setNivelreentrenamiento(t.getIdnivel() == NivelEnum.REENTRENAMIENTO.getCodigo() ? "X" : "");
		f.setNivelbasicooperativo(t.getIdnivel() == NivelEnum.BASICO_OPERATIVO.getCodigo() ? "X" : "");
		f.setActualizacioncoordinador(t.getIdnivel() == NivelEnum.ACTUALIZACION_COORDINADOR.getCodigo() ? "X" : "");
		f.setJefeareatrabajoalturas(t.getIdnivel() == NivelEnum.JEFE_DE_AREA.getCodigo() ? "X" : "");

		if (t.getTipovinculacionlaboral() == null) {
			f.setTipovinculacionlaboraldependiente("");
			f.setTipovinculacionlaboralindependiente("");
			f.setTipovinculacionlaboralninguno("");
		} else {
			f.setTipovinculacionlaboraldependiente(t.getTipovinculacionlaboral().equals("D") ? "X" : "");
			f.setTipovinculacionlaboralindependiente(t.getTipovinculacionlaboral().equals("I") ? "X" : "");
			f.setTipovinculacionlaboralninguno(t.getTipovinculacionlaboral().equals("N") ? "X" : "");
		}

		if (t.getRegimenafiliacionseguridadsocial() == null) {
			f.setRegimenafiliacionseguridadsocialcontributivo("");
			f.setRegimenafiliacionseguridadsocialsubsidiado("");
			f.setRegimenafiliacionseguridadsocialotro("");
		} else {
			f.setRegimenafiliacionseguridadsocialcontributivo(
					t.getRegimenafiliacionseguridadsocial().equals("C") ? "X" : "" );
			f.setRegimenafiliacionseguridadsocialsubsidiado(
					t.getRegimenafiliacionseguridadsocial().equals("S") ? "X" : "" );
			f.setRegimenafiliacionseguridadsocialotro(
					t.getRegimenafiliacionseguridadsocial().equals("O") ? "X" : "" );
		}

		f.setDocumentoidentidad(
				t.getDocumentoidentidad() == null || t.getDocumentoidentidad().equals("N") ? "" : "X");
		f.setUltimopagoseguridadsocial(
				t.getUltimopagoseguridadsocial() == null || t.getUltimopagoseguridadsocial().equals("N") ? "" : "X");
		f.setAfiliacionseguridadsocial(
				t.getAfiliacionseguridadsocial() == null || t.getAfiliacionseguridadsocial().equals("N") ? "" : "X");
		f.setCertificadoaptitudmedica(
				t.getCertificadoaptitudmedica() == null || t.getCertificadoaptitudmedica().equals("N") ? "" : "X");

		f.setEmbarazo(t.getEmbarazo()== null || t.getEmbarazo().equals("N") ? "NO" : "SI");
		f.setMesesgestacion(t.getMesesgestacion());

		f.setReentrenamiento(t.getCertificadotrabajadorautorizado() == null || t.getCertificadotrabajadorautorizado().equals("N") ? "" : "X");
		f.setActualizadocoordinador(t.getCertificadonivelcoordinador() == null || t.getCertificadonivelcoordinador().equals("N") ? "" : "X");

		f.setSourcefirma(extaerFirmaService.extraerFirma(AprendizId.toLong(t.getId())));

		return f;
	}

	public Formatoevaluacion generarFormatoEvaluacionPojo(Aprendiz t)  {
		Formatoevaluacion f = new Formatoevaluacion();
				
		f.setTipodocumento(t.getTrabajador().getTipodocumento());
		f.setNumerodocumento(t.getTrabajador().getNumerodocumento());
		f.setNombrecompleto(t.getTrabajador().getNombrecompleto());

		f.setPrimernombre(t.getTrabajador().getPrimernombre());
		f.setSegundonombre(t.getTrabajador().getSegundonombre());
		f.setPrimerapellido(t.getTrabajador().getPrimerapellido());
		f.setSegundoapellido(t.getTrabajador().getSegundoapellido());

		f.setEteorica(t.getEteorica1().toString());
		f.setEpractica(t.getEpractica().toString());
		f.setEenfasis(t.getEenfasis().toString());
		f.setEtotal("0");
				
		return f;
	}
	
	public List<FormatoEvaluacionPractica> generarFormatoEvaluacionPojo(
			Aprendiz t, String tipoEvaluacion)  {
		List<FormatoEvaluacionPractica> fs = new ArrayList<>();
				
		List<Grupopregunta> gs;
		
		gs = grupoDao.findEvaluacionAprendiz(tipoEvaluacion);
		
		for (Grupopregunta g : gs) {
			List<Pregunta> preguntas;

			preguntas = preguntaDao.findByIdgrupoIdnivel(g.getId(),
					t.getIdnivel() == null ? null : t.getIdnivel().longValue());
			
			for (Pregunta pregunta : preguntas) {
				Evaluacion e = evaluacionDao.findEvaluacion(
						AprendizId.toLong(t.getId()),
						pregunta.getId(),
						0);

				FormatoEvaluacionPractica f = new FormatoEvaluacionPractica();

				f.setNombregrupo(g.getNombre());
				f.setOrden(pregunta.getOrden());
				f.setNombrepregunta(pregunta.getPregunta());
				if (e != null) {
					f.setCumple(e.getRespuestacorrecta().equals("S") ? "X" : "");
					f.setNocumple(e.getRespuestacorrecta().equals("N") ? "X" : "");
				} else {
					f.setCumple("");
					f.setNocumple("X");
				}
				f.setNotapractica(t.getEpractica());
				f.setOrdengrupopregunta(g.getOrden());

				f.setSourcefirma(getPathFiles.getSignaturesPath() + "S" + t.getId() + ".png");
				fs.add(f);
			}
		}
		
		return fs;
	}

	public byte[] generarBytesPerfilIngresoReport(Aprendiz t)  {
		List<Perfilingreso> l = new ArrayList<>();
		Perfilingreso f = generarPerfilingresoPojo(t);
		
		l.add(f);

		JasperReport report = IreportUtil.getJasperReport("perfilingreso.jrxml",
				t.getTrabajador().getNumerodocumento());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(l);
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("createBy", "xx");

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);

			byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

			return pdf;
		} catch (JRException e) {
			log.error("Se presento un error generando el reporte ", e);
			throw new RuntimeException("Erro generando el reporte");
		}
	}
	
	public byte[] generarBytesEvaluacionPracticaReport(Aprendiz t) throws JRException, BusinessException {
		List<FormatoEvaluacionPractica> l;
		
		l = generarFormatoEvaluacionPojo(t, "P");

		JasperReport report = IreportUtil.getJasperReport("evaluacionpractica.jrxml", t.getTrabajador().getNumerodocumento());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(l);
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("createBy", "xx");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
		
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		return pdf;
	}

	public byte[] generarBytesEvaluacionTeoricaReport(Aprendiz t) throws FileNotFoundException, JRException, BusinessException {
		List<FormatoEvaluacionPractica> l;
		
		l = generarFormatoEvaluacionPojo(t, "T");

		JasperReport report = IreportUtil.getJasperReport("evaluacionteorica.jrxml",
				t.getTrabajador().getNumerodocumento());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(l);
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("createBy", "xx");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
		
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		return pdf;
	}
	
	public byte[] generarBytesFormatoEvaluacionReport(Aprendiz t) throws BusinessException, JRException, FileNotFoundException {
		List<Formatoevaluacion> l = new ArrayList<>();
		Formatoevaluacion f = generarFormatoEvaluacionPojo(t);
		
		l.add(f);
		JasperReport report = IreportUtil.getJasperReport("formatoevaluacion.jrxml", t.getTrabajador().getNumerodocumento());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(l);
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("createBy", "xx");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
		
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		return pdf;
	}

	public String exporterPerfilingresoReport(Long idaprendiz)  {
		Aprendiz t = findById(idaprendiz);
		String pdfBase64;
		
		if (Objects.isNull(t)) {
			return "No existe trabajador";
		}

		byte[] pdf = generarBytesPerfilIngresoReport(t);

		pdfBase64 = Base64.getEncoder().encodeToString(pdf);

		return pdfBase64 == null ? "Sin datos" : pdfBase64;
	}
	
	public String exporterFormatoevaluacionReport(Long idaprendiz) throws FileNotFoundException, JRException {
		Aprendiz t = findById(idaprendiz);
		String pdfBase64;
		
		if (t == null) {
			return "No existe trabajador";
		}

		try {			
			byte[] pdf = generarBytesEvaluacionPracticaReport(t);
	
	        pdfBase64 = Base64.getEncoder().encodeToString(pdf);
	
			return pdfBase64 == null ? "Sin datos" : pdfBase64;
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	
	public String exporterEvaluacionPacticaReport(Long idaprendiz) throws FileNotFoundException, JRException {
		Aprendiz t = findById(idaprendiz);
		String pdfBase64;
		
		if (t == null) {
			return "No existe trabajador";
		}

		try {			
			byte[] pdf = generarBytesFormatoEvaluacionReport(t);
	
	        pdfBase64 = Base64.getEncoder().encodeToString(pdf);
	
			return pdfBase64 == null ? "Sin datos" : pdfBase64;
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	public String exporterFichaCompletaReport(Long idaprendiz) throws FileNotFoundException, JRException {
		Aprendiz t = findById(idaprendiz);
		String pdfBase64;
		
		if (t == null) {
			return "No existe aprendiz con ID: " + idaprendiz;
		}

		try {			
			byte[] pdfFichaInscripcion = generarBytesFormatoinscripcionReport(t, null);
			byte[] pdfPerfilIngreso = generarBytesPerfilIngresoReport(t);
			byte[] pdfFormatoEvaluacion = generarBytesFormatoEvaluacionReport(t);
			byte[] pdfFormatoEvaluacionPractica = generarBytesEvaluacionPracticaReport(t);
			byte[] pdfFormatoEvaluacionTeorica = generarBytesEvaluacionTeoricaReport(t);
			
			List<InputStream> list = new ArrayList<InputStream>();
	        try {
	            list.add(new ByteArrayInputStream(pdfFichaInscripcion));
	            list.add(new ByteArrayInputStream(pdfPerfilIngreso));
	            list.add(new ByteArrayInputStream(pdfFormatoEvaluacion));
	            list.add(new ByteArrayInputStream(pdfFormatoEvaluacionPractica));
	            list.add(new ByteArrayInputStream(pdfFormatoEvaluacionTeorica));
	            
	            pdfBase64 = itextMerge.doMerge(list);
		    	
				return pdfBase64 == null ? "Sin datos" : pdfBase64;
	        } catch (Exception e) {
	            throw e;
	        }
		} catch (Exception e) {
			return e.toString();
		}
	}
}

