package com.seguridadlimite.models.aprendiz.application.findByFiltro;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizEvaluacionDTO;
import com.seguridadlimite.models.aprendiz.application.mapper.AprendizEvaluacionMapper;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AprendizFindByFilter {

	enum DataType {
		NUMERIC,
		DATE,
		STRING
	}

	private final AprendizEvaluacionMapper aprendizMapper;

	private final IAprendizDao dao;


	public List<AprendizEvaluacionDTO> findByFiltro(String filtro) {
		List<Aprendiz> aprendizs;

		switch (getDataType(filtro)) {
			case STRING:
				aprendizs = buscarPorNombreInteligente(filtro);
				break;
			case NUMERIC:
				aprendizs = dao.findBynumerodocumento(filtro);
				break;
			case DATE:
				aprendizs = dao.findByFechaInscripcion(filtro);
				break;
			default:
				aprendizs = new ArrayList<>();
		}

		return aprendizs.stream()
				.map(aprendizMapper::toDto)
				.sorted(Comparator.<AprendizEvaluacionDTO, String>comparing(dto -> dto.getNivel().getNombre())
						.thenComparing(AprendizEvaluacionDTO::getNombreCompletoTrabajador))
				.toList();
	}

	private String quitarTildes(String texto) {
		String textoSinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD);
		textoSinTildes = textoSinTildes.replaceAll("[^\\p{ASCII}]", "");
		return textoSinTildes;
	}

	private List<Aprendiz> buscarPorNombreInteligente(String searchTerm) {
		String[] palabras = searchTerm.trim().split("\\s+");

		if (searchTerm.startsWith("id-")) {

		}

		List<Aprendiz> resultadosFinales = new ArrayList<>();
		for (String palabra : palabras) {
			if (palabra.length() > 3) {
				List<Aprendiz> resultadosParciales = dao.findByNombre(palabra);
				if (resultadosFinales.isEmpty()) {
					resultadosFinales.addAll(resultadosParciales);
				} else {
					resultadosFinales.retainAll(resultadosParciales);
				}
			}
		}

		// Filtrar la lista final para que contenga todas las palabras del término de búsqueda
		return resultadosFinales.stream()
				.filter(aprendiz -> contieneTodasPalabras(aprendiz, palabras))
				.collect(Collectors.toList());
	}

	private boolean contieneTodasPalabras(Aprendiz aprendiz, String[] palabras) {
		String nombreCompleto = aprendiz.getTrabajador().getPrimernombre() + " "
				+ aprendiz.getTrabajador().getSegundonombre() + " "
				+ aprendiz.getTrabajador().getPrimerapellido() + " "
				+ aprendiz.getTrabajador().getSegundoapellido();

		// Eliminar las tildes del nombre completo
		nombreCompleto = quitarTildes(nombreCompleto.toLowerCase());

		for (String palabra : palabras) {
			if (palabra.length() > 3 && !nombreCompleto.contains(palabra.toLowerCase())) {
				return false;
			}
		}
		return true;
	}
	private DataType getDataType(String str) {
		if (str == null || str.isEmpty()) {
			return DataType.STRING;
		}

		// Intentar parsear como número
		try {
			Long.parseLong(str);
			return DataType.NUMERIC;
		} catch (NumberFormatException e) {
			// No es numérico, continuar
		}

		// Intentar parsear como fecha con diferentes formatos
		String[] formatos = {"yyyy-MM-dd", "yyyy-MM"};
		for (String formato : formatos) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			dateFormat.setLenient(false);
			try {
				Date date = dateFormat.parse(str);
				return DataType.DATE;
			} catch (ParseException e) {
				// Intentar con el siguiente formato
			}
		}

		return DataType.STRING; // No es numérico ni fecha
	}


	public List<AprendizEvaluacionDTO> findByNit(String filtro) {
		List<Aprendiz> aprendizs = dao.findByNitCertificado(filtro);

		return aprendizs.stream()
				.map(aprendizMapper::toDto)
				.sorted(Comparator.<AprendizEvaluacionDTO, String>comparing(dto -> dto.getNivel().getNombre())
						.thenComparing(AprendizEvaluacionDTO::getNombreCompletoTrabajador))
				.toList();
	}
}
