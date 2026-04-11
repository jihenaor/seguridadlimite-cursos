package com.seguridadlimite.models.encuesta.application;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.seguridadlimite.config.GlobalConstants;
import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.encuesta.domain.PdfEncuesta;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import com.seguridadlimite.models.quiz.application.ConsultarQuizAprendizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@AllArgsConstructor
public class GenerarPdfEncuestaService {
    private final IAprendizDao aprendizDao;
    private final ConsultarQuizAprendizService service;

    public ByteArrayInputStream execute(long idaprendiz) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Aprendiz aprendiz = aprendizDao.findById(AprendizId.toInteger(idaprendiz)).orElseThrow();
        List<Pregunta> preguntas = service.findPreguntasAprendiz(
                idaprendiz,
                "C",
                0);

        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(out));
            Document document = new Document(pdfDoc);

            PdfEncuesta pdfEncuesta = PdfEncuesta.builder()
                .nombreAprendiz(aprendiz.getTrabajador().getNombrecompleto())
                .fechaEncuesta(aprendiz.getFechaencuesta())
                .comentariosEncuesta(aprendiz.getComentariosencuesta())
                .preguntas(preguntas)
                .logoBase64(GlobalConstants.LOGO_SEGURIDAD_LIMITE)
                .build();

            pdfEncuesta.generarPdf(document);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }







} 