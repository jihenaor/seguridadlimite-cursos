package com.seguridadlimite.models.aprendiz.application;

import com.google.zxing.WriterException;
import com.seguridadlimite.QRCodeGenerator;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.documentoaprendiz.domain.Documentoaprendiz;
import com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz.DocumentoaprendizServiceImpl;
import com.seguridadlimite.models.documentos.application.DocumentoServiceImpl;
import com.seguridadlimite.models.documentos.domain.Documento;
import com.seguridadlimite.models.trabajador.application.BuscarDocumentoTrabajador.BuscarDocumentoTrabajadorService;
import com.seguridadlimite.models.trabajador.application.TrabajadorService;
import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AprendizServicerImpl {

	private final GetPathFiles getPathFiles;
	private final IAprendizDao dao;
	private final TrabajadorService trabajadorService;
	private final BuscarDocumentoTrabajadorService buscarDocumentoTrabajadorService;
	private final DocumentoServiceImpl documentoService;
	private final DocumentoaprendizServiceImpl documentoAprendizService;

	@Transactional(readOnly = true)
	public List<Aprendiz> findByIdtrabajador(Long idtrabajador) {
		return (List<Aprendiz>) dao.findByIdtrabajador(idtrabajador);
	}

	@Transactional(readOnly = true)
	public Aprendiz findById(Long id) {
		return id == null ? null : dao.findById(AprendizId.toInteger(id)).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public Aprendiz findByIdEmpresa(Long id) throws IOException {
		Aprendiz aprendiz = id == null ? null : dao.findById(AprendizId.toInteger(id)).orElse(null);

		if (Objects.isNull(aprendiz)) {
			return null;
		}
		
		if (aprendiz.getTrabajador().getExt() != null && !aprendiz.getTrabajador().getExt().isEmpty()) {
			buscarDocumentoTrabajadorService.getDocumentoLadoALadoB(aprendiz.getTrabajador());
		}

		var documentos = getDocumentos(aprendiz.getId() == null ? null : aprendiz.getId(), true);
		aprendiz.setDocumentos(documentos);
		
		return aprendiz;
	}

	@Transactional
	public Aprendiz save(Aprendiz t) {
		if (t.getAbonocurso() == null || t.getAbonocurso().isEmpty()) {
			t.setAbonocurso("N");
		}
		
		if (t.getCumplehoras() == null || t.getCumplehoras().isEmpty()) {
			t.setCumplehoras("N");
		}
		
		if (t.getEvaluacionformacion() == null) {
			t.setEvaluacionformacion((double) 0);
		}		
		
		if (t.getEvaluacionentrenamiento() == null) {
			t.setEvaluacionentrenamiento((double) 0);
		}

		if (t.getAlergias() == null) {
			t.setAlergias("");
		}

		if (t.getDrogas() == null) {
			t.setDrogas("");
		}

		if (t.getEenfasis() == null) {
			t.setEenfasis((double) 0);
		}

		if (t.getCargoactual() == null) {
			t.setCargoactual("");
		}
		
		if (t.getEteorica1() == null) {
			t.setEteorica1((double) 0);
		}

		if (t.getEteorica2() == null) {
			t.setEteorica2((double) 0);
		}

		if (t.getEpractica() == null) {
			t.setEpractica((double) 0);
		}
		
		if (t.getEstadoinscripcion() == null) {
			t.setEstadoinscripcion("I");
		}
		
		if (t.getPagocurso() == null) {
			t.setPagocurso("N");
		}
		
		if (t.getEmbarazo() == null) {
			t.setEmbarazo("N");
		}

		if (t.getMesesgestacion() == null) {
			t.setMesesgestacion("");
		}

		if (t.getIntentos() == null) {
			t.setIntentos((byte) 0);
		}
		
		if (t.getNiveleducativo() == null) {
			t.setNiveleducativo("");
		}

		if (t.getEps() == null) {
			t.setEps("");
		}

		if (t.getArl() == null) {
			t.setArl("");
		}

		return dao.save(t);
	}

	@Transactional
	public void delete(Long id) {
		if (id != null) {
			dao.deleteById(AprendizId.toInteger(id));
		}
	}
	
	private void generarBase64List(List<Aprendiz> l) {
		for (Aprendiz aprendiz : l) {
			String url = "https://cursos.seguridadallimite.com/#/authentication/checkqr/" + aprendiz.getId();
			try {
				String base64 = QRCodeGenerator.getQRCodeString(url, 300, 300);
				aprendiz.setBase64(base64);
			} catch (WriterException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Aprendiz> findByEmpresa(Long idempresa) {
		return null;
/*
		List<Aprendiz> l;
		
		l = dao.findByIdempresa(idempresa);
		
		generarBase64List(l);
		
		return l;

 */
	}

	public List<Documento> getDocumentos(int idaprendiz, Boolean documentosEmpresa) throws IOException {
		List<Documento> documentos;
		
		if (documentosEmpresa) {
			documentos = documentoService.findByDocumentosEmpresa();
		} else {
			documentos = documentoService.findByTipo("A");
		}
		
		for (Documento documento : documentos) {
			documento.setIdaprendiz(idaprendiz);

			Documentoaprendiz d  = documentoAprendizService.findByIdAprendiz(documento.getId(), idaprendiz);

			if (d != null) {
				documento.setExt(d.getExt());
				documento.setIddocumentoaprendiz(d.getId());

		        String base64File = "";
		        File file = new File(getPathFiles.getDocumentsPath() + "A" + d.getId() + "." + d.getExt());
		        try (FileInputStream imageInFile = new FileInputStream(file)) {
		            byte[] fileData = new byte[(int) file.length()];
		            if (imageInFile.read(fileData) > 0) {
			            base64File = Base64.getEncoder().encodeToString(fileData);
			            documento.setBase64(base64File);
		            }
		        }
            }
		}
		return documentos;		
	}
}
