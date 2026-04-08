package com.seguridadlimite.models.encuesta.domain;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.seguridadlimite.models.pregunta.domain.Pregunta;
import lombok.Builder;
import lombok.Getter;

import java.util.Base64;
import java.util.List;

@Getter
@Builder
public class PdfEncuesta {
    private static final String TITLE = "GESTIÓN INTEGRAL";
    private static final String CODE = "GI-FO-004";
    private static final String VERSION = "004";
    private static final String CREATION_DATE = "02/01/2018";
    private static final String LAST_MODIFIED_DATE = "27/03/2024";
    private static final String DOCUMENT_NAME = "FORMATO EVALUACIÓN DE LA SATISFACCIÓN DEL CLIENTE";

    private final String nombreAprendiz;
    private final String fechaEncuesta;
    private final String comentariosEncuesta;
    private final List<Pregunta> preguntas;
    private final String logoBase64;

    public void generarPdf(Document document) {
        addHeader(document);
        agregarInformacionEncuestado(document);
        agregarInformacionTexto(document);
        agregarInformacionGradoSatisfaccion(document);
        procesarPreguntas(document);
        agregarComentarios(document);
        agregarPiePagina(document);
    }

    private void addHeader(Document document) {
        float[] columnWidths = {1, 2, 1};
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100));

        agregarCeldaLogo(table);
        agregarCeldasHeader(table);

        document.add(table);
    }

    private void agregarCeldaLogo(Table table) {
        Cell cellLogo = new Cell(4, 1);
        
        try {
            byte[] imageBytes = Base64.getDecoder().decode(logoBase64);
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image img = new Image(imageData);
            img.setAutoScale(true);
            
            cellLogo.add(img);
            cellLogo.add(new Paragraph("Nit 900.824.368-6"))
                    .setTextAlignment(TextAlignment.CENTER);
        } catch (Exception e) {
            cellLogo.add(new Paragraph("Nit 900.824.368-6"))
                    .setTextAlignment(TextAlignment.CENTER);
        }
        table.addCell(cellLogo);
    }

    private void agregarCeldasHeader(Table table) {
        table.addCell(new Cell(2, 1)
                .add(new Paragraph(TITLE))
                .setTextAlignment(TextAlignment.CENTER));

        table.addCell(new Cell()
                .add(new Paragraph("Código: " + CODE))
                .setTextAlignment(TextAlignment.CENTER));

        table.addCell(new Cell()
                .add(new Paragraph("Versión: " + VERSION))
                .setTextAlignment(TextAlignment.CENTER));

        table.addCell(new Cell(2, 1)
                .add(new Paragraph(DOCUMENT_NAME))
                .setTextAlignment(TextAlignment.CENTER));

        table.addCell(new Cell()
                .add(new Paragraph("Fecha de elaboración"))
                .add(new Paragraph(CREATION_DATE))
                .setTextAlignment(TextAlignment.CENTER));

        table.addCell(new Cell()
                .add(new Paragraph("Fecha de última modificación"))
                .add(new Paragraph(LAST_MODIFIED_DATE))
                .setTextAlignment(TextAlignment.CENTER));
    }

    private void agregarInformacionEncuestado(Document document) {
        Table infoTable = new Table(2);
        infoTable.setWidth(UnitValue.createPercentValue(100));

        infoTable.addCell(new Cell()
                .add(new Paragraph("Nombre aprendiz encuestado: " + nombreAprendiz))
                .setTextAlignment(TextAlignment.LEFT));

        String fechaMostrar = fechaEncuesta != null && fechaEncuesta.length() > 10 
            ? fechaEncuesta.substring(0, 10) 
            : fechaEncuesta;

        infoTable.addCell(new Cell()
                .add(new Paragraph("Fecha: " + fechaMostrar))
                .setTextAlignment(TextAlignment.LEFT));

        document.add(infoTable);
    }

    private void agregarInformacionTexto(Document document) {
        Table textoTable = new Table(1);
        textoTable.setWidth(UnitValue.createPercentValue(100));

        Paragraph intro = new Paragraph(
            "Para el centro de formación y entrenamiento SEGURIDAD AL LIMITE S.A.S. es muy importante conocer sus observaciones para el " +
            "mejoramiento continuo. Por favor llene esta encuesta (rápida y hacemos saber su opinión).")
            .setTextAlignment(TextAlignment.JUSTIFIED);

        textoTable.addCell(new Cell()
                .add(intro)
                .setTextAlignment(TextAlignment.LEFT));

        document.add(textoTable);
    }

    private void procesarPreguntas(Document document) {
        List<String> agrupador1s = preguntas.stream()
                .map(Pregunta::getAgrupador1)
                .distinct()
                .toList();

        for (String agrupador1 : agrupador1s) {
            agregarInformacionUnaColumna100porciento(agrupador1, document, ColorFondo.AMARILLO);
            procesarAgrupador2(agrupador1, filtrarPreguntasPorAgrupador1(agrupador1), document);
        }
    }

    private List<Pregunta> filtrarPreguntasPorAgrupador1(String agrupador1) {
        return preguntas.stream()
                .filter(pregunta -> pregunta.getAgrupador1().equals(agrupador1))
                .toList();
    }

    private void procesarAgrupador2(String agrupador1, List<Pregunta> preguntasAgrupador1, Document document) {
        preguntasAgrupador1.stream()
                .map(Pregunta::getAgrupador2)
                .distinct()
                .forEach(agrupador2 -> procesarGrupoAgrupador2(agrupador1, agrupador2, preguntasAgrupador1, document));
    }

    private void procesarGrupoAgrupador2(String agrupador1, String agrupador2, List<Pregunta> preguntasAgrupador1, Document document) {
        if (agrupador2.startsWith("1.1")) {
            agregarInformacionDosColumnasColumnaAnchos(agrupador2, "Criterios de calificacion",
                    document, ColorFondo.GRIS, Alineacion.CENTRO, new float[]{650f, 150f});
        } else {
            agregarInformacionUnaColumna100porciento(agrupador2, document, ColorFondo.GRIS);
        }

        List<Pregunta> preguntasAgrupador2 = filtrarPreguntasPorAgrupador2(preguntasAgrupador1, agrupador1, agrupador2);
        List<String> agrupador3s = obtenerAgrupador3s(preguntasAgrupador2);
        
        agregarPreguntas(agrupador1, agrupador2, agrupador3s, preguntasAgrupador1, document);
    }

    private List<Pregunta> filtrarPreguntasPorAgrupador2(List<Pregunta> preguntasAgrupador1, String agrupador1, String agrupador2) {
        return preguntasAgrupador1.stream()
                .filter(pregunta -> pregunta.getAgrupador1().equals(agrupador1) && 
                                  pregunta.getAgrupador2().equals(agrupador2))
                .toList();
    }

    private List<String> obtenerAgrupador3s(List<Pregunta> preguntas) {
        return preguntas.stream()
                .map(Pregunta::getAgrupador3)
                .distinct()
                .toList();
    }

    private void agregarPreguntas(String agrupador1, String agrupador2, List<String> agrupador3s, 
                                List<Pregunta> preguntasAgrupador1, Document document) {
        for (String agrupador3: agrupador3s) {
            List<Pregunta> preguntasAgrupador3 = filtrarPreguntasAgrupador3(preguntasAgrupador1, agrupador1, agrupador2, agrupador3);
            if (!preguntasAgrupador3.isEmpty()) {
                Table evaluacionTable = crearTablaEvaluacion(agrupador1, preguntasAgrupador3);
                agregarPreguntasATabla(evaluacionTable, preguntasAgrupador3, agrupador1);
                document.add(evaluacionTable);
            }
        }
    }

    private List<Pregunta> filtrarPreguntasAgrupador3(List<Pregunta> preguntasAgrupador1,
                                                   String agrupador1,
                                                   String agrupador2,
                                                   String agrupador3) {
        return preguntasAgrupador1.stream()
                .filter(pregunta ->
                        pregunta.getAgrupador1().equals(agrupador1)
                                && pregunta.getAgrupador2().equals(agrupador2)
                                && pregunta.getAgrupador3().equals(agrupador3))
                .toList();
    }

    private Table crearTablaEvaluacion(String agrupador1, List<Pregunta> preguntasAgrupador3) {
        return agrupador1.startsWith("1") ? 
               crearTablaConPrimeraColumna(preguntasAgrupador3) : 
               crearTablaSinPrimeraColumna();
    }

    private Table crearTablaConPrimeraColumna(List<Pregunta> preguntasAgrupador3) {
        float[] anchos = {120f, 500f, 30f, 30f, 30f, 30f, 30f};
        Table evaluacionTable = new Table(anchos);
        evaluacionTable.setWidth(UnitValue.createPercentValue(100));

        Cell primeraColumna = new Cell(preguntasAgrupador3.size(), 1)
                .add(new Paragraph(preguntasAgrupador3.get(0).getAgrupador3()))
                .setBackgroundColor(ColorFondo.GRIS.getColor())
                .setMinWidth(120f)
                .setMaxWidth(120f)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        evaluacionTable.addCell(primeraColumna);

        return evaluacionTable;
    }

    private Table crearTablaSinPrimeraColumna() {
        float[] anchos = {500f, 30f, 30f, 30f, 30f, 30f};
        Table evaluacionTable = new Table(anchos);
        evaluacionTable.setWidth(UnitValue.createPercentValue(100));
        return evaluacionTable;
    }

    private void agregarPreguntasATabla(Table evaluacionTable, List<Pregunta> preguntasAgrupador3, String agrupador1) {
        preguntasAgrupador3.forEach(pregunta -> {
            agregarCeldaPregunta(evaluacionTable, pregunta, agrupador1);
            agregarCeldasRespuesta(evaluacionTable, pregunta);
        });
    }

    private void agregarCeldaPregunta(Table evaluacionTable, Pregunta pregunta, String agrupador1) {
        Cell cellPregunta = new Cell()
            .add(new Paragraph(pregunta.getPregunta()))
            .setVerticalAlignment(VerticalAlignment.MIDDLE);

        if (!agrupador1.startsWith("1")) {
            cellPregunta.setBackgroundColor(ColorFondo.GRIS.getColor());
        }

        evaluacionTable.addCell(cellPregunta);
    }

    private void agregarCeldasRespuesta(Table evaluacionTable, Pregunta pregunta) {
        for (int i = 1; i <= 5; i++) {
            evaluacionTable.addCell(crearCeldaRespuesta(pregunta, String.valueOf(i)));
        }
    }

    private Cell crearCeldaRespuesta(Pregunta pregunta, String valor) {
        return new Cell()
            .add(new Paragraph(pregunta.getRespuestacorrecta() != null && 
                             valor.equals(pregunta.getRespuestacorrecta()) ? "X": ""))
            .setTextAlignment(TextAlignment.CENTER)
            .setVerticalAlignment(VerticalAlignment.MIDDLE);
    }

    private void agregarInformacionUnaColumna100porciento(String texto, Document document, 
                                                       ColorFondo colorFondo) {
        Table textoTable = new Table(1);
        textoTable.setWidth(UnitValue.createPercentValue(100));

        Cell textoCell = new Cell()
                .add(new Paragraph(texto)
                        .setTextAlignment(Alineacion.CENTRO.getAlignment()))
                .setTextAlignment(Alineacion.CENTRO.getAlignment());
        
        if (colorFondo.getColor() != null) {
            textoCell.setBackgroundColor(colorFondo.getColor());
        }

        textoTable.addCell(textoCell);
        document.add(textoTable);
    }

    private void agregarInformacionDosColumnasColumnaAnchos(
            String texto1,
            String texto2,
            Document document,
            ColorFondo colorFondo,
            Alineacion alineacion,
            float[] anchos) {
        Table textoTable = new Table(anchos);
        textoTable.setWidth(UnitValue.createPercentValue(100));

        agregarCeldaTexto(textoTable, texto1, colorFondo, alineacion);
        agregarCeldaTexto(textoTable, texto2, colorFondo, alineacion);

        document.add(textoTable);
    }

    private void agregarCeldaTexto(Table table, String texto, ColorFondo colorFondo, Alineacion alineacion) {
        Cell cell = new Cell()
                .add(new Paragraph(texto)
                        .setTextAlignment(alineacion.getAlignment()))
                .setTextAlignment(alineacion.getAlignment());

        if (colorFondo.getColor() != null) {
            cell.setBackgroundColor(colorFondo.getColor());
        }

        table.addCell(cell);
    }

    private void agregarInformacionGradoSatisfaccion(Document document) {
        agregarEncabezadoGradoSatisfaccion(document);
        agregarCriteriosCalificacion(document);
    }

    private void agregarEncabezadoGradoSatisfaccion(Document document) {
        Table escalaTable = new Table(1);
        escalaTable.setWidth(UnitValue.createPercentValue(100));
        
        escalaTable.addCell(new Cell()
            .add(new Paragraph("Exprese su grado de satisfacción de acuerdo a los siguientes criterios, donde"))
            .setTextAlignment(TextAlignment.LEFT)
            .setBorderBottom(null));
        
        document.add(escalaTable);
    }

    private void agregarCriteriosCalificacion(Document document) {
        Table criteriosTable = new Table(5);
        criteriosTable.setWidth(UnitValue.createPercentValue(100));

        String[] criterios = {
            "1 - Muy Deficiente",
            "2- Deficiente",
            "3- Regular",
            "4- Bueno",
            "5- Excelente"
        };

        for (String criterio : criterios) {
            Cell cell = new Cell()
                .add(new Paragraph(criterio))
                .setBorderRight(null)
                .setBorderTop(null);
            
            if (!criterio.equals(criterios[0])) {
                cell.setBorderLeft(null);
            }
            
            criteriosTable.addCell(cell);
        }

        document.add(criteriosTable);
    }

    private void agregarComentarios(Document document) {
        Table comentariosTable = new Table(new float[]{350f, 30f, 30f, 200f});
        comentariosTable.setWidth(UnitValue.createPercentValue(100));

        comentariosTable.addCell(new Cell()
                .add(new Paragraph("¿Tiene algún comentario o sugerencia con respecto a la capacitación?")));
                
        boolean tieneComentarios = comentariosEncuesta != null && !comentariosEncuesta.isEmpty();
        
        comentariosTable.addCell(new Cell()
                .add(new Paragraph("SI" + (tieneComentarios ? ": X" : ""))));
        comentariosTable.addCell(new Cell()
                .add(new Paragraph("NO" + (!tieneComentarios ? ": X" : ""))));
        comentariosTable.addCell(new Cell()
                .add(new Paragraph("Indique cual: " + comentariosEncuesta)));

        document.add(comentariosTable);
    }

    private void agregarPiePagina(Document document) {
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("AYUDANOS A MEJORAR")
            .setFontSize(16)
            .setBold()
            .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Gracias por participar de nuestros programas de formación y entrenamiento")
            .setFontSize(10)
            .setTextAlignment(TextAlignment.CENTER));
    }
} 