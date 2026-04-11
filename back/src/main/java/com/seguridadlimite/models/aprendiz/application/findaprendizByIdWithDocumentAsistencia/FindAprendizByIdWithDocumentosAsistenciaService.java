package com.seguridadlimite.models.aprendiz.application.findaprendizByIdWithDocumentAsistencia;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.asistencia.application.FindAsistenciaAprendizCu;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import com.seguridadlimite.models.documentoaprendiz.domain.Documentoaprendiz;
import com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz.DocumentoaprendizServiceImpl;
import com.seguridadlimite.models.documentos.application.DocumentoServiceImpl;
import com.seguridadlimite.models.documentos.domain.Documento;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.models.trabajador.application.BuscarDocumentoTrabajador.BuscarDocumentoTrabajadorService;
import com.seguridadlimite.models.trabajador.application.EncodeFoto.EncodeFotoTrabajadorService;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.util.EncodeFileToBase64;
import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindAprendizByIdWithDocumentosAsistenciaService {

	private final GetPathFiles getPathFiles;


	private final IAprendizDao aprendizDao;

	private final DocumentoServiceImpl documentoService;

	private final DocumentoaprendizServiceImpl documentoAprendizService;

	private final FindAsistenciaAprendizCu asistenciaAprendiz;

	private final EncodeFotoTrabajadorService encodeFotoTrabajadorService;

	private final BuscarDocumentoTrabajadorService buscarDocumentoTrabajadorService;

	@Autowired
	EncodeFileToBase64 encodeFile;

	private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;



	@Transactional
	public Aprendiz find(Long id) {
		Aprendiz aprendiz = aprendizDao.findById(AprendizId.toInteger(id)).orElseThrow();

		consultarDocumentos(aprendiz);
		encodeFotoTrabajadorService.encodeFoto(aprendiz.getTrabajador());

		try {
			buscarDocumentoTrabajadorService.getDocumentoLadoALadoB(aprendiz.getTrabajador());
		} catch (BusinessException businessException) {
			// Manejo de excepciones, se puede registrar un log
		}

		consultarAsistencia(id, aprendiz);
		calcularHorasAsistidas(aprendiz);
		return aprendiz;
	}

	private void consultarAsistencia(Long id, Aprendiz aprendiz) {
		aprendiz.setAsistencias(asistenciaAprendiz.find(id));
	}

	@Transactional
	private void calcularHorasAsistidas(Aprendiz aprendiz) {
		double totalHoras = aprendiz.getAsistencias().stream()
				.filter(asistencia -> asistencia.getFecha() != null && !asistencia.getFecha().isEmpty())
				.mapToDouble(Asistencia::getHoras)
				.sum();

		aprendiz.setTotalhoras(totalHoras);

		if (aprendiz.getIdPermiso() == null) {
			if (aprendiz.getNivel().getDuraciontotal() == totalHoras) {

				Optional<String> fechaUltimaAsistencia = obtenerFechaMasReciente(aprendiz);
				if (fechaUltimaAsistencia.isPresent()) {
					List<PermisoTrabajoAlturas> permisoTrabajoAlturas = permisoTrabajoAlturasPort.findPermisosVigentesEnFecha(fechaUltimaAsistencia.get(), aprendiz.getIdnivel());

					if (!permisoTrabajoAlturas.isEmpty()) {
						Integer idPermiso = permisoTrabajoAlturas.get(0).getIdPermiso();

						aprendizDao.updatePermisotrabajo(idPermiso, aprendiz.getId());
					}
				}
			}
		}
	}

	public Optional<String> obtenerFechaMasReciente(Aprendiz aprendiz) {
		return aprendiz.getAsistencias().stream()
				.filter(asistencia -> asistencia.getFecha() != null && !asistencia.getFecha().isEmpty())
				.map(Asistencia::getFecha)
				.max(Comparator.naturalOrder());
	}

	private void consultarDocumentos(Aprendiz a) {
		List<Documento> documentos = documentoService.findByTipo("A");

		for (Documento documento : documentos) {
			documento.setIdaprendiz(a.getId());
			asociarAprendizDocumento(a, documento);
		}
		a.setDocumentos(documentos);
	}

	private void asociarAprendizDocumento(Aprendiz a, Documento documento) {
		Documentoaprendiz d = documentoAprendizService.findByIdAprendiz(documento.getId(), a.getId());

		if (d != null) {
			documento.setExt(d.getExt());
			documento.setIddocumentoaprendiz(d.getId());

			File file = new File(getPathFiles.getDocumentsPath() + "A" + d.getId() + "." + d.getExt());

			// Encode file to Base64 if it exists
			if (file.exists()) {
				String base64File = encodeFile.encode(getPathFiles.getDocumentsPath() + "A" + d.getId() + "." + d.getExt());
				documento.setBase64(base64File);
			}
		}
	}
}