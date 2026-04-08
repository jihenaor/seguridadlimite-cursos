package com.seguridadlimite.util;

import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class IreportUtil {
    
    /**
     * Obtiene y compila un reporte Jasper desde un archivo JRXML
     */


    public static JasperReport getJasperReport(String nombreArchivoInforme, String referencia) throws BusinessException {
        JasperReport report;
        try {
            String archivoJasper = nombreArchivoInforme.replace(".jrxml", ".jasper");

            // Obtener el InputStream del archivo .jasper desde el classpath
            InputStream inputStream = IreportUtil.class.getClassLoader().getResourceAsStream("" + archivoJasper);
            if (inputStream == null) {
                throw new BusinessException("El archivo requerido para generar el informe no existe: " + archivoJasper);
            }

            report = (JasperReport) JRLoader.loadObject(inputStream);
            inputStream.close();

        } catch (Exception e) {
            log.error("Error cargando el reporte {} Documento: {}", nombreArchivoInforme, referencia, e);
            throw new BusinessException("Error generando el informe. Ref: " + referencia);
        }
        return report;
    }


    public static JasperReport getJasperReportCompilando(String nombreArchivoInforme,
        String referencia) throws BusinessException {
            JasperReport report;
            try {

                URL fileURL = IreportUtil.class.getClassLoader().getResource("Blank_Letter_1.jasper");
                if (fileURL == null) {
                    log.error("No se encontró el archivo: /{}", nombreArchivoInforme);
                } else {
                    log.info("Archivo encontrado: {}", fileURL.getPath());
                }

                // Obtener el InputStream del archivo desde el classpath
                InputStream inputStream = IreportUtil.class.getClassLoader().getResourceAsStream(nombreArchivoInforme);
                if (inputStream == null) {
                    throw new BusinessException("El archivo requerido para generar el informe no existe: " + nombreArchivoInforme);
                }

                // Leer el contenido del InputStream
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[8192]; // Buffer más grande para mejor rendimiento
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }

                // Compilar el reporte
                report = JasperCompileManager.compileReport(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

                // Cerrar los streams
                inputStream.close();
                byteArrayOutputStream.close();

            } catch (Exception e) {
                log.error("Error compilando el reporte {} Documento: {}", nombreArchivoInforme, referencia, e);
                throw new BusinessException("Error generando el informe. Ref: " + referencia);
            }
            return report;
        }

    /**
     * Genera un PDF a partir de un JasperReport, parámetros y datos
     */
    public static byte[] generatePdfReport(JasperReport jasperReport, 
                                         Map<String, Object> parameters, 
                                         Collection<?> data) throws BusinessException {
        try {
            // Crear el datasource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
            
            // Llenar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            
            return JasperExportManager.exportReportToPdf(jasperPrint);
            
        } catch (Exception e) {
            log.error("Error generando el PDF del reporte", e);
            throw new BusinessException("Error generando el PDF del informe");
        }
    }

    /**
     * Genera un PDF directamente desde un archivo JRXML
     */
    public static byte[] generatePdfFromJrxml(String nombreArchivoInforme, 
                                            Map<String, Object> parameters, 
                                            Collection<?> data,
                                            String referencia) throws BusinessException {
        JasperReport jasperReport = getJasperReport(nombreArchivoInforme, referencia);
        return generatePdfReport(jasperReport, parameters, data);
    }
}
