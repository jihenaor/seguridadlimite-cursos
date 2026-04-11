package com.seguridadlimite.springboot.backend.apirest.services;

import com.seguridadlimite.models.aprendiz.application.departamentos.DepartamentoServiceImpl;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.IEvaluacionDao;
import com.seguridadlimite.models.entity.Documentoevaluacion;
import com.seguridadlimite.models.entity.Formatoevaluacion;
import com.seguridadlimite.models.grupopregunta.infraestructure.IGrupopreguntaDao;
import com.seguridadlimite.models.pregunta.infraestructure.IPreguntaDao;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class AprendizServiceImpl {

	@Autowired
	private IAprendizDao dao;

	@Autowired
	private IGrupopreguntaDao grupoDao;
	
	@Autowired
	private IPreguntaDao preguntaDao;
	
	@Autowired
	private IEvaluacionDao evaluacionDao;

	@Autowired
	private DepartamentoServiceImpl departamentoService;

	@Transactional(readOnly = true)
	public List<Aprendiz> findAll() {
		return (List<Aprendiz>) dao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Aprendiz> findByCodigoVerificacion() {
		return dao.findByCodigoVerificacion();
	}

	@Transactional(readOnly = true)
	public List<Aprendiz> findByCodigoVerificacionIsNull() {
		return dao.findByCodigoVerificacionIsnull();
	}

	@Transactional(readOnly = true)
	public List<Aprendiz> findByNumeroDocumento(String numeroDocumento) {
		return dao.findByTrabajadorNumerodocumento(numeroDocumento);
	}

	public List<Aprendiz> findByNumeroDocumentoIn(List<String> numerosDocumento) {
		return dao.findByTrabajadorNumerodocumentoIn(numerosDocumento);
	}

	@Transactional(readOnly = true)
	public Aprendiz findById(Long id) {
		return id == null ? null : dao.findById(AprendizId.toInteger(id)).orElse(null);
	}

	@Transactional
	public Aprendiz save(Aprendiz entity) {
		entity.setPagocurso(entity.getPagocurso() == null ? "N" : entity.getPagocurso());
		entity.setEvaluacionformacion(entity.getEvaluacionformacion() == null ? 0.0 : entity.getEvaluacionformacion());

		entity.setEvaluacionentrenamiento(entity.getEvaluacionentrenamiento() == null ? 0.0 : entity.getEvaluacionentrenamiento());
		entity.setCumplehoras(entity.getCumplehoras() == null ? "N" : entity.getCumplehoras());
		entity.setAbonocurso(entity.getAbonocurso() == null ? "N" : entity.getAbonocurso());
		entity.setEingreso(entity.getEingreso() == null ? 0.0 : entity.getEingreso());
		entity.setEenfasis(entity.getEenfasis() == null ? 0.0 : entity.getEenfasis());
		entity.setEteorica1(entity.getEteorica1() == null ? 0.0 : entity.getEteorica1());
		entity.setEteorica2(entity.getEteorica2() == null ? 0.0 : entity.getEteorica2());
		entity.setEpractica(entity.getEpractica() == null ? 0.0 : entity.getEpractica());
		entity.setEstadoinscripcion(entity.getEstadoinscripcion() == null ? "I" : entity.getEstadoinscripcion());
		entity.setEmbarazo(entity.getEmbarazo() == null ? "N" : entity.getEmbarazo());
		entity.setMesesgestacion(entity.getMesesgestacion() == null ? "" : entity.getMesesgestacion());
		entity.setEps(entity.getEps() == null ? "" : entity.getEps());
		entity.setArl(entity.getArl() == null ? "" : entity.getArl());
		entity.setSabeleerescribir(entity.getSabeleerescribir() == null || entity.getSabeleerescribir().isEmpty() ? "S" : entity.getSabeleerescribir());
		entity.setLabordesarrolla(entity.getLabordesarrolla() == null ? "" : entity.getLabordesarrolla());
		entity.setAlergias(entity.getAlergias() == null ? "" : entity.getAlergias());
		entity.setMedicamentos(entity.getMedicamentos() == null ? "" : entity.getMedicamentos());
		entity.setEnfermedades(entity.getEnfermedades() == null ? "" : entity.getEnfermedades());
		entity.setLesiones(entity.getLesiones() == null ? "" : entity.getLesiones());
		entity.setDrogas(entity.getDrogas() == null ? "" : entity.getDrogas());
		entity.setNombrecontacto(entity.getNombrecontacto() == null ? "" : entity.getNombrecontacto());
		entity.setTelefonocontacto(entity.getTelefonocontacto() == null ? "" : entity.getTelefonocontacto());
		entity.setRegimenafiliacionseguridadsocial(entity.getRegimenafiliacionseguridadsocial() == null ? "" : entity.getRegimenafiliacionseguridadsocial());
		entity.setTipovinculacionlaboral(entity.getTipovinculacionlaboral() == null ? "" : entity.getTipovinculacionlaboral());


    	  if (entity.getParentescocontacto() == null) {
    		  entity.setParentescocontacto("");
    	  }

      	  if (entity.getTieneexperienciaaltura() == null) {
      		  entity.setTieneexperienciaaltura("S");
      	  }
    	  
      	  if (entity.getNiveleducativo() == null) {
      		  entity.setNiveleducativo("");
      	  }
      	  
      	  if (entity.getCargoactual() == null) {
      		entity.setCargoactual("");
      	  }

      	if (entity.getTienefirma() == null) {
      		entity.setTienefirma("N");
  	  	}
		entity.setIntentos(entity.getIntentos() == null ? 0 : entity.getIntentos());
		entity.setContadorcertificados(entity.getContadorcertificados() == null ? 0 : entity.getContadorcertificados());
		return dao.save(entity);
	}

	@Transactional
	public void delete(Long id) {
		if (id != null) {
			dao.deleteById(AprendizId.toInteger(id));
		}
	}

	public List<Aprendiz> findByNumerodocumento(String numerodocumento) {
		return dao.findBynumerodocumento(numerodocumento);
	}
	/*
	public Aprendiz findBynumerodocumentogrupo(String numerodocumento, Long idgrupo) {
		return dao.findBynumerodocumentogrupo(numerodocumento, idgrupo);
	}
*/
	public List<Aprendiz> findEvaluacionByidgrupo(Long idgrupo) {
		List<Aprendiz> l = null;
		  
//		l = dao.findByIdgrupo(idgrupo);
		/*
		for (Aprendiz aprendiz : l) {
			int i = 1;

		}
*/
		return l;
	}
	
	public void update(List<Aprendiz> aprendizs) {
		for(Aprendiz aprendiz : aprendizs) {
			Aprendiz a = aprendiz.getId() == null ? null : dao.findById(aprendiz.getId()).orElse(null);
			
			if (a != null) {
				if (aprendiz.getEenfasis() == null) {
					a.setEenfasis((double)0);
				}
				a.setEenfasis(aprendiz.getEenfasis());
				a.setEteorica1(aprendiz.getEteorica1());
				a.setEteorica2(aprendiz.getEteorica2());
				a.setEpractica(aprendiz.getEpractica());
				
				dao.save(a);
			}
		}
	}

	@Transactional(readOnly = true)
	public Aprendiz findAprendizInscripcion(String numerodocumento) {
		return dao.findBynumerodocumentoGrupoActivo(numerodocumento);
	}
	
	@Transactional
	public void saveDocumentoevaluacion(Documentoevaluacion t) throws FileNotFoundException {
		switch (t.getTipo()) {
			case "T":
				dao.updateExteteorica(t.getExt(), AprendizId.toInteger(t.getIdaprendiz()));
				break;
			case "P":
				dao.updateExtepractica(t.getExt(), AprendizId.toInteger(t.getIdaprendiz()));
				break;
			default:
				break;
		}
//		trabajador.setAdjuntodocumento("S");
//		trabajador.setExt(t.getExt());
		
		if (t.getBase64() != null && !t.getBase64().isEmpty()) {

				byte[] decodedString = Base64.getDecoder().decode(t.getBase64().getBytes(StandardCharsets.UTF_8));

				try (FileOutputStream fos = new FileOutputStream("/home/wwsegu/public_html/uploads/E"
						+ t.getTipo()
						+ t.getIdaprendiz() + "." + t.getExt())) {
					   fos.write(decodedString);
					   //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
					} catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }
	}


	public Formatoevaluacion generarFormatoEvaluacionPojo(Aprendiz t) throws FileNotFoundException, JRException {
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

	@Transactional
	public int updateIntentos(Long idaprendiz) {
		return dao.updateIntentos(AprendizId.toInteger(idaprendiz));
	}

	@Transactional
	public int reiniciarIntentos(Long idaprendiz) {
		return dao.reiniciarIntentos(AprendizId.toInteger(idaprendiz));
	}

}

