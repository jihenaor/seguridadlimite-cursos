package com.seguridadlimite.models.pregunta.application.inicializarimagenes;

import com.seguridadlimite.models.entity.Respuesta;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.util.EncodeFile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@AllArgsConstructor
public class InicializarImagenes {

	private final EncodeFile encodeFile;

	public void inicializarImagenes(List<Pregunta> l) {
        try {
            for (Pregunta pregunta : l) {
                getImageQuestion(pregunta);

                getImageResponse(pregunta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

    private void getImageResponse(Pregunta pregunta) {
        List<Respuesta> respuestas = pregunta.getRespuestas();
        for (Respuesta respuesta : respuestas) {
            if ("S".equals(respuesta.getTieneimagen()) && respuesta.getNombreimagen() != null) {
                String resourceName = "/images/" + respuesta.getNombreimagen() + ".png"; // Ajusta la ruta según tu estructura

                try (InputStream is = getClass().getResourceAsStream(resourceName)) {
                    if (is != null) {
                        respuesta.setBase64(encodeFile.encodeFromInputStream(is));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getImageQuestion(Pregunta pregunta) {
        if (pregunta.getDiflectura()!=null
                && "S".equals(pregunta.getDiflectura())) {
            String resourceName = "/images/p" + pregunta.getOrden() + ".png";
            try (InputStream is = getClass().getResourceAsStream(resourceName)) {
                if (is != null) {
                    pregunta.setBase64(encodeFile.encodeFromInputStream(is));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
