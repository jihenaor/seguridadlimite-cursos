package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.config.GlobalConstants;
import com.seguridadlimite.models.aprendiz.application.extraerfirma.ExtraerFirmaServiceImpl;
import com.seguridadlimite.models.aprendiz.application.inscribiraprendiz.PermisotrabajoalturasAprendicesInfo;
import com.seguridadlimite.models.aprendiz.application.inscribiraprendiz.PermisotrabajoalturasInfo;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.asistencia.application.FindAsistenciaFechaCu;
import com.seguridadlimite.models.entity.ReportePojo;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.util.IreportUtil;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PermisoTrabajoAlturasService {
	private final ExtraerFirmaServiceImpl extraerFirmaService;
	private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;
	private final FindAsistenciaFechaCu findAsistenciaFechaCu;
	private final IAprendizDao aprendizDao;

	public ReportePojo exporterPermisoTrabajoAlturasReport(Integer idPermiso, int grupo) throws JRException, BusinessException {
		PermisoTrabajoAlturas permisoTrabajoAlturas = getPermisoTrabajoAlturas(idPermiso);

		JasperReport report = IreportUtil.getJasperReport("GTH-FO-014-V06.jasper",
				"Id permiso: " + idPermiso);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fromAggregate(
				permisoTrabajoAlturas,
				findAprendices(permisoTrabajoAlturas.getIdPermiso()),
				grupo));

		Map<String, Object> parameters = new HashMap<>();

		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
		
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        String pdfBase64 = Base64.getEncoder().encodeToString(pdf);

		return new ReportePojo(pdfBase64);
	}

	private PermisoTrabajoAlturas getPermisoTrabajoAlturas(Integer idPermiso) {
		return permisoTrabajoAlturasPort.findById(idPermiso).orElseThrow();
	}

	private List<PermisotrabajoalturasInfo> fromAggregate(
			PermisoTrabajoAlturas aggregate,
			List<PermisotrabajoalturasAprendicesInfo> permisotrabajoalturasAprendicesInfos,
			int grupo) throws BusinessException {

		if (permisotrabajoalturasAprendicesInfos.size() <= 10 && grupo == 2) {
			throw new BusinessException("El numero de aprendices es menor a 10 (" +
					permisotrabajoalturasAprendicesInfos.size() + "), no aplica para un segundo grupo en el permiso");
		}

        PermisotrabajoalturasInfo.PermisotrabajoalturasInfoBuilder builder = PermisotrabajoalturasInfo.builder();
        builder.idPermiso(aggregate.getIdPermiso());
        builder.anio(aggregate.getFechaInicio().substring(0, 4));
        builder.mes(aggregate.getFechaInicio().substring(5, 6));
        builder.dia(aggregate.getFechaInicio().substring(8, 8));
        builder.validodesde(aggregate.getValidodesde());
        builder.validohasta(aggregate.getValidohasta());
        builder.horaInicio(aggregate.getHorainicio());
        builder.horaFinal(aggregate.getHorafinal());
        builder.proyectoAreaSeccion(aggregate.getProyectoAreaSeccion());
        builder.ubicacionEspecifica(aggregate.getUbicacionEspecifica());
        builder.descripcionTarea(aggregate.getDescripcionTarea());
        builder.herramientasUtilizar(aggregate.getHerramientasUtilizar());
        builder.alturaTrabajo(aggregate.getAlturaTrabajo());
        builder.idNivel(aggregate.getIdNivel());
        builder.verificacionSeguridadSocial(Boolean.TRUE.equals(aggregate.getVerificacionSeguridadSocial()) ? "X" : "");
        builder.certificadoAptitudMedica(Boolean.TRUE.equals(aggregate.getCertificadoAptitudMedica()) ? "X" : "");
        builder.certificadoCompetencia(Boolean.TRUE.equals(aggregate.getCertificadoCompetencia()) ? "X" : "");
        builder.condicionSaludTrabajador(Boolean.TRUE.equals(aggregate.getCondicionSaludTrabajador()) ? "X" : "");
        builder.codigoministerio(aggregate.getCodigoministerio());
        builder.cupoinicial(aggregate.getCupoinicial());
        builder.cupofinal(aggregate.getCupofinal());

		if (aggregate.getPersonaautoriza1() == null) {
			throw new BusinessException("Error - Debe digitar persona que autoriza");
		}

		if (grupo == 1) {
			builder.nombreapellidoentrenador(aggregate.getPersonaautoriza1().getNombrecompleto());
			builder.documentoentrenador(aggregate.getPersonaautoriza1().getNumerodocumento());

			builder.nroCcAutoriza(aggregate.getPersonaautoriza1().getNumerodocumento());
			builder.personaAutoriza(aggregate.getPersonaautoriza1().getNombrecompleto());

			builder.nroCcCoordinador(aggregate.getPersonaautoriza1().getNumerodocumento());
			builder.coordinadorAlturas(aggregate.getPersonaautoriza1().getNombrecompleto());
		} else {
			builder.nombreapellidoentrenador(aggregate.getPersonaautoriza2().getNombrecompleto());
			builder.documentoentrenador(aggregate.getPersonaautoriza2().getNumerodocumento());

			builder.nroCcAutoriza(aggregate.getPersonaautoriza2().getNumerodocumento());
			builder.personaAutoriza(aggregate.getPersonaautoriza2().getNombrecompleto());

			builder.nroCcCoordinador(aggregate.getPersonaautoriza2().getNumerodocumento());
			builder.coordinadorAlturas(aggregate.getPersonaautoriza2().getNombrecompleto());
		}

		builder.nroCcResponsable(aggregate.getResponsableemeergencias().getNumerodocumento());
		builder.responsableEmergencias(aggregate.getResponsableemeergencias().getNombrecompleto());


		builder.logoseguridad(GlobalConstants.LOGO_SEGURIDAD_LIMITE);
        PermisotrabajoalturasInfo info = builder.build();

		asignarTipoTrabajo(aggregate, info);

		// Mapear los detalles de chequeo si existen
		if (aggregate.getPermisoDetalleChequeos() != null && !aggregate.getPermisoDetalleChequeos().isEmpty()) {
			info.setDetalleselementosproteccion(
					aggregate.getPermisoDetalleChequeos().stream()
							.filter(permisoDetalleChequeo
									-> permisoDetalleChequeo.getGrupoChequeo().getIdGrupo().equals(1))
							.map(detalle -> PermisotrabajoalturasInfo.PermisoDetalleChequeoInfo.builder()
									.descripcion(detalle.getDescripcion())
									.respuesta(detalle.getRespuesta())
									.build())
							.toList()
			);

			info.setVerificacionpreoperacional(
					aggregate.getPermisoDetalleChequeos().stream()
							.filter(permisoDetalleChequeo
									-> permisoDetalleChequeo.getGrupoChequeo().getIdGrupo().equals(2))
							.map(detalle -> PermisotrabajoalturasInfo.PermisoDetalleChequeoInfo.builder()
									.descripcion(detalle.getDescripcion())
									.respuesta(detalle.getRespuesta())
									.build())
							.toList()
			);

			info.setDetalleslistaverificacion(
					aggregate.getPermisoDetalleChequeos().stream()
							.filter(permisoDetalleChequeo
									-> permisoDetalleChequeo.getGrupoChequeo().getIdGrupo().equals(3))
							.map(detalle -> PermisotrabajoalturasInfo.PermisoDetalleChequeoInfo.builder()
									.descripcion(detalle.getDescripcion())
									.respuesta(detalle.getRespuesta())
									.build())
							.toList()
			);

			info.setDetallesanalisistarea(
					aggregate.getPermisoDetalleChequeos().stream()
							.filter(permisoDetalleChequeo
									-> permisoDetalleChequeo.getGrupoChequeo().getIdGrupo().equals(4))
							.map(detalle -> PermisotrabajoalturasInfo.PermisoDetalleChequeoInfo.builder()
									.descripcion(detalle.getDescripcion())
									.respuesta(detalle.getRespuesta())
									.build())
							.toList()
			);
		}

		info.setDetallesactividad(aggregate.getPermisoDetalleActividades().stream()
				.map(permisoDetalleActividad -> PermisotrabajoalturasInfo.PermisoDetalleActividadInfo.builder()
						.actividadrealizar(permisoDetalleActividad.getActividadRealizar())
						.peligros(permisoDetalleActividad.getPeligros())
						.controlesrequeridos(permisoDetalleActividad.getControlesRequeridos())
						.build())
				.toList()
		);


		int startIndex = (grupo - 1) * 10;
		int endIndex = Math.min(startIndex + 10, permisotrabajoalturasAprendicesInfos.size());

		List<PermisotrabajoalturasAprendicesInfo> subLista = permisotrabajoalturasAprendicesInfos.subList(startIndex, endIndex);

		info.setAprendices(subLista);

		List<PermisotrabajoalturasInfo> arrayList = new ArrayList<>();

		arrayList.add(info);

		return arrayList;
	}

	private static void asignarTipoTrabajo(PermisoTrabajoAlturas aggregate, PermisotrabajoalturasInfo info) {
		aggregate.getTiposTrabajo().forEach(permisoTipoTrabajo -> {
			switch (permisoTipoTrabajo.getTipoTrabajo().toLowerCase()) {
				case "fachada":
					info.setFachada("X");
					break;
				case "estructura":
					info.setEstructura("X");
					break;
				case "poste":
					info.setPoste("X");
					break;
				case "reticula":
					info.setReticula("X");
					break;
				case "cubierta":
					info.setCubierta("X");
					break;
				case "suspension":
					info.setSuspension("X");
					break;
				case "andamios":
					info.setAndamios("X");
					break;
				case "torre electrica":
					info.setTorre("X");
					break;
				default: break;
			}
		});
	}

	private List<PermisotrabajoalturasAprendicesInfo> findAprendices(int idPermiso) {
		List<PermisotrabajoalturasAprendicesInfo> aprendicesInfos = new ArrayList<>();

		List<Aprendiz> aprendizs = aprendizDao.findByIdPermiso(idPermiso);

		aprendizs.forEach(aprendiz -> aprendicesInfos.add(new PermisotrabajoalturasAprendicesInfo(
                aprendiz.getTrabajador().getNombrecompleto(),
                aprendiz.getTrabajador().getTipodocumento() + "-" + aprendiz.getTrabajador().getNumerodocumento(),
				extraerFirmaService.extraerFirma(AprendizId.toLong(aprendiz.getId()))
        )));

		return aprendicesInfos;
	}
}
