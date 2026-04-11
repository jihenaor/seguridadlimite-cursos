package com.seguridadlimite.models.trabajador.application;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.asistencia.application.FindAsistenciaCompletaCu;
import com.seguridadlimite.models.asistencia.application.RegisterAsistenciaAprendizService;
import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.nivel.application.findInscripcionesAbiertas.FindInscripcionesAbiertasCu;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.services.AprendizServicerImpl2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrabajadorService {

	private final ITrabajadorDao trabajadorDao;

	private final IAprendizDao aprendizDao;

	private final AprendizServicerImpl2 aprendizService;

	private final RegisterAsistenciaAprendizService asistenciaService;

	@Autowired
	FindAsistenciaCompletaCu findAsistenciaCompletaCu;

	private final FindInscripcionesAbiertasCu validaExisteInscripcionAbierta;

	private final TrabajadorMapper trabajadorMapper;

	@Transactional(readOnly = true)
	public List<Trabajador> findAll() {
		return (List<Trabajador>) trabajadorDao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Trabajador> findTrabajadoresGruposActivos() {
//		return (List<Trabajador>) trabajadorDao.findGruposActivos();
		return null;
	}

	@Transactional(readOnly = true)
	public Trabajador findById(Long id) {
		return trabajadorDao.findById(id).orElse(null);
	}

	@Transactional
	public void deletAll() {
		trabajadorDao.deleteAll();
	}
/*
	@Transactional(readOnly = true)
	public List<Trabajador> findByIdgrupo(Long idgrupo) {
		return trabajadorDao.findByIdgrupo(idgrupo);
	}
*


	@Transactional
	public void delete(Long id) {
		trabajadorDao.deleteById(id);
	}



/*
	@Transactional(readOnly = true)
	public TrabajadorInscripcionPojo findTrabajadorInscripcion(String numerodocumento) throws BusinessException {
		TrabajadorInscripcionPojo trabajadorInscripcionPojo = new TrabajadorInscripcionPojo();

		boolean existeInscripcionAbierta = validaExisteInscripcionAbierta.validar();

		trabajadorInscripcionPojo.setExisteinscripcionabierta(existeInscripcionAbierta);

		Trabajador t = trabajadorDao.findBynumerodocumento(numerodocumento);

		if (t != null) {
			trabajadorInscripcionPojo.setId(t.getId());
			trabajadorInscripcionPojo.setTipodocumento(t.getTipodocumento());
			trabajadorInscripcionPojo.setNumerodocumento(t.getNumerodocumento());
			trabajadorInscripcionPojo.setPrimernombre(t.getPrimernombre());
			trabajadorInscripcionPojo.setSegundonombre(t.getSegundonombre());
			trabajadorInscripcionPojo.setPrimerapellido(t.getPrimerapellido());
			trabajadorInscripcionPojo.setSegundoapellido(t.getSegundoapellido());
			trabajadorInscripcionPojo.setGenero(t.getGenero());
			trabajadorInscripcionPojo.setNacionalidad(t.getNacionalidad());
			trabajadorInscripcionPojo.setTiposangre(t.getTiposangre());
			trabajadorInscripcionPojo.setOcupacion(t.getOcupacion());

			trabajadorInscripcionPojo.setCelular(t.getCelular());
			trabajadorInscripcionPojo.setCorreoelectronico(t.getCorreoelectronico());

			Aprendiz aprendiz = aprendizDao.findByIdtrabajadorInscripcion(calcularFechaInicio(), t.getId());

			if (aprendiz != null) {
				trabajadorInscripcionPojo.setIdaprendiz(aprendiz.getId());
				trabajadorInscripcionPojo.setIdgrupo(aprendiz.getIdgrupo());
				trabajadorInscripcionPojo.setIdnivel(aprendiz.getIdnivel());
				trabajadorInscripcionPojo.setNombrenivel(aprendiz.getGrupo().getNivel().getNombre());
				trabajadorInscripcionPojo.setIdenfasis(aprendiz.getIdenfasis());
				trabajadorInscripcionPojo.setNombreenfasis(aprendiz.getEnfasis().getNombre());
				trabajadorInscripcionPojo.setOtroenfasis(aprendiz.getOtroenfasis());
				trabajadorInscripcionPojo.setLabordesarrolla(aprendiz.getLabordesarrolla());
				trabajadorInscripcionPojo.setEmpresa(aprendiz.getEmpresa());
				trabajadorInscripcionPojo.setNit(aprendiz.getNit());
				trabajadorInscripcionPojo.setRepresentantelegal(aprendiz.getRepresentantelegal());
				trabajadorInscripcionPojo.setTieneexperienciaaltura(aprendiz.getTieneexperienciaaltura());

				trabajadorInscripcionPojo.setLabordesarrolla(aprendiz.getLabordesarrolla());

				trabajadorInscripcionPojo.setDirecciondomicilio(aprendiz.getDirecciondomicilio());
				trabajadorInscripcionPojo.setDepartamentodomicilio(aprendiz.getDepartamentodomicilio());
				trabajadorInscripcionPojo.setCiudaddomicilio(aprendiz.getCiudaddomicilio());

				trabajadorInscripcionPojo.setEps(aprendiz.getEps());
				trabajadorInscripcionPojo.setArl(aprendiz.getArl());
				trabajadorInscripcionPojo.setSabeleerescribir(aprendiz.getSabeleerescribir());
				trabajadorInscripcionPojo.setNiveleducativo(aprendiz.getNiveleducativo());
				trabajadorInscripcionPojo.setCargoactual(aprendiz.getCargoactual());
				trabajadorInscripcionPojo.setAlergias(aprendiz.getAlergias());
				trabajadorInscripcionPojo.setMedicamentos(aprendiz.getMedicamentos());
				trabajadorInscripcionPojo.setEnfermedades(aprendiz.getEnfermedades());
				trabajadorInscripcionPojo.setLesiones(aprendiz.getLesiones());
				trabajadorInscripcionPojo.setDrogas(aprendiz.getDrogas());
				trabajadorInscripcionPojo.setNombrecontacto(aprendiz.getNombrecontacto());
				trabajadorInscripcionPojo.setTelefonocontacto(aprendiz.getTelefonocontacto());
				trabajadorInscripcionPojo.setParentescocontacto(aprendiz.getParentescocontacto());

				trabajadorInscripcionPojo.setEmbarazo(aprendiz.getEmbarazo());
				trabajadorInscripcionPojo.setMesesgestacion(aprendiz.getMesesgestacion());
				trabajadorInscripcionPojo.setEingreso(aprendiz.getEingreso());
				trabajadorInscripcionPojo.setFechainscripcion(aprendiz.getFechainscripcion());
				trabajadorInscripcionPojo.setFechalimiteinscripcion(aprendiz.getFechalimiteinscripcion());
				if (aprendiz.getFechaencuesta() != null) {
					trabajadorInscripcionPojo.setFechaencuesta(aprendiz.getFechaencuesta());
				}

				List<GrupoDto> grupoDtos = findGruposActivosInscripcionNivel.findGruposActivosNivel(aprendiz.getIdnivel());

				trabajadorInscripcionPojo.setGrupos(grupoDtos);
			}
		}

		return trabajadorInscripcionPojo;
	}
	*/

	private Date calcularFechaInicio() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -30);
		return cal.getTime();
	}
}
