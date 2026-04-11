package com.seguridadlimite.models.aprendiz.application.findTrabajadorInscripcion;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.asistencia.application.FindAsistenciaCompletaCu;
import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.entity.TrabajadorInscripcionPojo;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.models.trabajador.application.EncodeFoto.ValidarExisteFotoTrabajadorService;
import com.seguridadlimite.models.trabajador.application.TrabajadorMapper;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FindTrabajadorInscripcionCu {

	private final ITrabajadorDao trabajadorDao;
	private final IAprendizDao aprendizDao;
	private final FindAsistenciaCompletaCu findAsistenciaCompletaCu;
	private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;
	private final TrabajadorMapper trabajadorMapper;
	private final ValidarExisteFotoTrabajadorService validarExisteFotoTrabajadorService;

	@Transactional
	public TrabajadorInscripcionPojo findTrabajadorInscripcion(String numerodocumento) throws BusinessException {
		InscripcionData data = procesarInscripcion(numerodocumento);

		if (data.trabajador == null) {
			TrabajadorInscripcionPojo pojo = new TrabajadorInscripcionPojo();
			pojo.setExisteinscripcionabierta(data.inscripcionAbierta);
			pojo.setAprendizContinuaAprendizaje(data.aprendizContinuaAprendizaje);
			return pojo;
		}

		validarExisteFotoTrabajadorService.exist(data.trabajador);

		return trabajadorMapper.toPojo(
				data.trabajador,
				data.aprendiz,
				data.inscripcionAbierta,
				data.asistenciaCompleta,
				data.aprendizContinuaAprendizaje
		);
	}

	private InscripcionData procesarInscripcion(String numerodocumento) throws BusinessException {
		Trabajador trabajador = trabajadorDao.findBynumerodocumento(numerodocumento);
		boolean inscripcionAbierta = permisoTrabajoAlturasPort.existenInscripcionesActivas(DateUtil.getCurrentDate());
		boolean aprendizContinuaAprendizaje = false;
		boolean asistenciaCompleta = false;
		Aprendiz aprendiz = null;

		log.info(String.format("inicia procesar inscripcion %s.  Trabajador existe: %s", numerodocumento, trabajador != null));
		if (trabajador != null) {

			aprendiz = aprendizDao
					.findUltimoInscritoByTrabajadorId(calcularFechaInicio(), trabajador.getId(), PageRequest.of(0, 1))
					.stream()
					.findFirst()
					.orElse(null);
			log.info(String.format("Aprendiz existe: %s", aprendiz != null));

			if (aprendiz != null) {
				asistenciaCompleta = findAsistenciaCompletaCu.find(AprendizId.toLong(aprendiz.getId()));

				log.info(String.format("Asistencia completa: %s", asistenciaCompleta));

				if (aprendiz.getFechaUltimaAsistencia() == null) {
					aprendizContinuaAprendizaje = true;
				} else {
					List<PermisoTrabajoAlturas> permisos = permisoTrabajoAlturasPort.findPermisosVigentesEnFechaIdNivel(
							aprendiz.getFechaUltimaAsistencia(), aprendiz.getIdnivel());

					for (PermisoTrabajoAlturas permiso : permisos) {
						if (aprendiz.getIdnivel() != null
								&& permiso.getIdNivel() != null
								&& aprendiz.getIdnivel().intValue() == permiso.getIdNivel()
								&& isFechaValida(permiso)) {
							int fechaAsistencia = parseFecha(aprendiz.getFechaUltimaAsistencia());
							int desde = parseFecha(permiso.getValidodesde());
							int hasta = parseFecha(permiso.getValidohasta());

							aprendizContinuaAprendizaje = fechaAsistencia >= desde && fechaAsistencia <= hasta;
							break;
						}
					}

					if (permisos.isEmpty()) {
						aprendizContinuaAprendizaje = true;
					}
				}

			}
		}

		return new InscripcionData(trabajador, aprendiz, inscripcionAbierta, asistenciaCompleta, aprendizContinuaAprendizaje);
	}

	private static boolean isFechaValida(PermisoTrabajoAlturas permiso) {
		return permiso.getValidodesde() != null &&
				permiso.getValidohasta() != null &&
				permiso.getValidohasta().length() == 10;
	}

	private static int parseFecha(String fecha) {
		return Integer.parseInt(fecha.replace("-", ""));
	}

	private String calcularFechaInicio() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -30);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	private record InscripcionData(
			Trabajador trabajador,
			Aprendiz aprendiz,
			boolean inscripcionAbierta,
			boolean asistenciaCompleta,
			boolean aprendizContinuaAprendizaje
	) {}
}
