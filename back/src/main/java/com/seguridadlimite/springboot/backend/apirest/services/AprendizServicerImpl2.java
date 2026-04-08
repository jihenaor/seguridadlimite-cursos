package com.seguridadlimite.springboot.backend.apirest.services;

import com.seguridadlimite.models.documentoaprendiz.updateDocumentoAprendiz.DocumentoaprendizServiceImpl;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.documentos.application.DocumentoServiceImpl;
import com.seguridadlimite.models.documentos.domain.Documento;
import com.seguridadlimite.models.documentoaprendiz.domain.Documentoaprendiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class AprendizServicerImpl2 {
	@Value(value = "${path.documents:/root/images/documents/}")
	private String documentsPath;

	@Autowired
	private IAprendizDao dao;


	@Autowired
	private DocumentoServiceImpl documentoService;
	
	@Autowired
	private DocumentoaprendizServiceImpl documentoAprendizService;
	
	@Transactional(readOnly = true)
	public List<Aprendiz> findAll() {
		return (List<Aprendiz>) dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Aprendiz> findByIdtrabajador(Long idtrabajador) {
		return dao.findByIdtrabajador(idtrabajador);
	}

	@Transactional(readOnly = true)
	public List<Aprendiz> findByIdtrabajadorrsintrabajador(Long idtrabajador) {
		return dao.findByIdtrabajador(idtrabajador);
	}
	
	@Transactional(readOnly = true)
	public List<Aprendiz> findByIdtrabajadorrsintrabajador(Long idtrabajador, Long idgrupo) {
//		return dao.findByIdtrabajador(idtrabajador, idgrupo);
		return null;
	}


	@Transactional(readOnly = true)
	public Aprendiz findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	public List<Documento> getDocumentos(Long idaprendiz, Boolean documentosEmpresa) throws IOException {
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
		        File file = new File( documentsPath + "A" + d.getId() + "." + d.getExt());
		        try (FileInputStream imageInFile = new FileInputStream(file)) {
		            byte fileData[] = new byte[(int) file.length()];
		            if (imageInFile.read(fileData) > 0) {
			            base64File = Base64.getEncoder().encodeToString(fileData);
			            documento.setBase64(base64File);
		            }
		        } catch (FileNotFoundException e) {
		            // System.out.println("File not found" + e);
		        } catch (IOException ioe) {
		            throw ioe;
		        }
			}
		}
		return documentos;		
	}	
	

}
