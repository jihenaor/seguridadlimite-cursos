package com.seguridadlimite.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.Base64;
import java.util.List;

@Component
public class ItextMerge {

    /**
     * Une múltiples archivos PDF en uno solo
     * @param inputPdfList Lista de rutas de los archivos PDF a unir
     * @param outputPath Ruta donde se guardará el PDF resultante
     * @throws IOException Si ocurre un error durante la lectura o escritura
     */
    public void mergePdfFiles(List<String> inputPdfList, String outputPath) throws IOException {
        // Inicializar el documento de salida
        PdfWriter writer = new PdfWriter(outputPath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        
        // Procesar cada PDF de entrada
        for (String inputPdf : inputPdfList) {
            PdfReader reader = new PdfReader(inputPdf);
            PdfDocument sourcePdf = new PdfDocument(reader);
            
            // Copiar todas las páginas del PDF fuente al documento destino
            sourcePdf.copyPagesTo(1, sourcePdf.getNumberOfPages(), pdfDoc);
            
            // Cerrar el documento fuente
            sourcePdf.close();
            reader.close();
        }
        
        // Cerrar el documento final
        pdfDoc.close();
        writer.close();
    }

    public String doMerge(List<InputStream> streams) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        
        for (InputStream stream : streams) {
            PdfReader reader = new PdfReader(stream);
            PdfDocument sourcePdf = new PdfDocument(reader);
            sourcePdf.copyPagesTo(1, sourcePdf.getNumberOfPages(), pdfDoc);
            sourcePdf.close();
            reader.close();
        }
        
        pdfDoc.close();
        writer.close();
        
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
} 