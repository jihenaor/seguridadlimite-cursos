package com.seguridadlimite.models.aprendiz.application.updateAprendiz;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateAprendizService {

	private final IAprendizDao dao;

	public void update(Aprendiz aprendiz) throws BusinessException {
		Aprendiz a = dao.findById(aprendiz.getId()).orElseThrow();

		fromAgregate(aprendiz, a);

		dao.save(a);
	}

	private static void fromAgregate(Aprendiz aprendiz, Aprendiz a) {
		a.setPagocurso(aprendiz.getPagocurso());
		a.setCumplehoras(aprendiz.getCumplehoras());
		a.setIdenfasis(aprendiz.getIdenfasis());
		a.setIdnivel(aprendiz.getIdnivel());

		a.setLabordesarrolla(aprendiz.getLabordesarrolla());

		a.setEps(aprendiz.getEps());
		a.setArl(aprendiz.getArl());
		a.setSabeleerescribir(aprendiz.getSabeleerescribir());
		a.setNiveleducativo(aprendiz.getNiveleducativo());
		a.setCargoactual(aprendiz.getCargoactual());
		a.setAlergias(aprendiz.getAlergias());
		a.setMedicamentos(aprendiz.getMedicamentos());
		a.setEnfermedades(aprendiz.getEnfermedades());
		a.setLesiones(aprendiz.getLesiones());
		a.setDrogas(aprendiz.getDrogas());
		a.setNombrecontacto(aprendiz.getNombrecontacto().toUpperCase());
		a.setTelefonocontacto(aprendiz.getTelefonocontacto());
		a.setParentescocontacto(aprendiz.getParentescocontacto().toUpperCase());
		a.setEmbarazo(aprendiz.getEmbarazo());
		a.setMesesgestacion(aprendiz.getMesesgestacion());

		a.setTipovinculacionlaboral(aprendiz.getTipovinculacionlaboral());
		a.setRegimenafiliacionseguridadsocial(aprendiz.getRegimenafiliacionseguridadsocial());
		a.setDocumentoidentidad(aprendiz.getDocumentoidentidad());
		a.setUltimopagoseguridadsocial(aprendiz.getUltimopagoseguridadsocial());
		a.setAfiliacionseguridadsocial(aprendiz.getAfiliacionseguridadsocial());
		a.setCertificadoaptitudmedica(aprendiz.getCertificadoaptitudmedica());

		a.setCertificadotrabajadorautorizado(aprendiz.getCertificadotrabajadorautorizado());
		a.setCertificadonivelcoordinador(aprendiz.getCertificadonivelcoordinador());

		a.setEvaluacionformacion(aprendiz.getEvaluacionformacion());
		a.setEvaluacionentrenamiento(aprendiz.getEvaluacionentrenamiento());

		a.setFechaemision(aprendiz.getFechaemision());
		a.setFechareentrenamiento(aprendiz.getFechareentrenamiento());
		a.setCiudadexpedicion(aprendiz.getCiudadexpedicion());
		a.setCiudadreentrenamiento(aprendiz.getCiudadreentrenamiento());
		a.setCodigoverificacion(aprendiz.getCodigoverificacion());

		a.setEmpresa(aprendiz.getEmpresa());
		a.setNit(aprendiz.getNit());
		a.setRepresentantelegal(aprendiz.getRepresentantelegal());
		a.setFechainscripcion(aprendiz.getFechainscripcion());
		a.setTotalhoras(aprendiz.getTotalhoras());

		a.setTipovinculacionlaboral(aprendiz.getTipovinculacionlaboral());
		a.setRegimenafiliacionseguridadsocial(aprendiz.getRegimenafiliacionseguridadsocial());

		a.setDirecciondomicilio(aprendiz.getDirecciondomicilio());
		a.setDepartamentodomicilio(aprendiz.getDepartamentodomicilio());
		a.setCiudaddomicilio(aprendiz.getCiudaddomicilio());
	}
}
