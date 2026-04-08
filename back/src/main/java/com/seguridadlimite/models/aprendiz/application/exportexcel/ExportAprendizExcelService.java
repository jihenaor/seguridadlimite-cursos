package com.seguridadlimite.models.aprendiz.application.exportexcel;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizExcelDTO;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportAprendizExcelService {

    private final IAprendizDao aprendizDao;

    public byte[] exportToExcel() {
        List<Aprendiz> aprendices = (List<Aprendiz>)aprendizDao.findByCodigoVerificacion();
        List<AprendizExcelDTO> dtos = aprendices.stream()
                .map(this::mapToExcelDTO)
                .collect(Collectors.toList());

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Aprendices");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID MIN. TRABAJO", "CODIGO", "FECHA INICIAL", "FECHA", "NOMBRE", 
                              "CEDULA", "NIVEL", "ENFASIS", "# CONTACTO", "EMPRESA", "NIT", 
                              "PAGADO", "SALDO", "ENTRENADOR"};
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }

            // Create data rows
            int rowNum = 1;
            for (AprendizExcelDTO dto : dtos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dto.getCodigoministerio() != null ? dto.getCodigoministerio().toString() : "");
                row.createCell(1).setCellValue(dto.getCodigo());
                row.createCell(2).setCellValue(dto.getFechaInicial() != null ? dto.getFechaInicial().toString() : "");
                row.createCell(3).setCellValue(dto.getFecha() != null ? dto.getFecha().toString() : "");
                row.createCell(4).setCellValue(dto.getNombre());
                row.createCell(5).setCellValue(dto.getCedula());
                row.createCell(6).setCellValue(dto.getNivel());
                row.createCell(7).setCellValue(dto.getEnfasis());
                row.createCell(8).setCellValue(dto.getNumeroContacto());
                row.createCell(9).setCellValue(dto.getEmpresa());
                row.createCell(10).setCellValue(dto.getNit());
                row.createCell(11).setCellValue(dto.getPagado());
                row.createCell(12).setCellValue(dto.getSaldo() != null ? dto.getSaldo() : 0.0);
                row.createCell(13).setCellValue(dto.getEntrenador());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el archivo Excel", e);
        }
    }

    private AprendizExcelDTO mapToExcelDTO(Aprendiz aprendiz) {
        AprendizExcelDTO dto = new AprendizExcelDTO();
        dto.setCodigo(aprendiz.getCodigoverificacion());
        if (aprendiz.getPermisoTrabajoAlturas() != null) {
            dto.setCodigoministerio(aprendiz.getPermisoTrabajoAlturas().getCodigoministerio() == null ? "" : aprendiz.getPermisoTrabajoAlturas().getCodigoministerio());
            dto.setFechaInicial(aprendiz.getPermisoTrabajoAlturas().getFechaInicio());
            dto.setFecha(aprendiz.getPermisoTrabajoAlturas().getValidoHasta());
        }
        dto.setNombre(aprendiz.getTrabajador().getNombrecompleto());
        dto.setCedula(aprendiz.getTrabajador().getNumerodocumento());
        dto.setNivel(aprendiz.getNivel() != null ? aprendiz.getNivel().getNombre() : "");
        dto.setEnfasis(aprendiz.getEnfasis() != null ? aprendiz.getEnfasis().getNombre() : "");
        dto.setNumeroContacto(aprendiz.getTrabajador().getCelular());

        dto.setEmpresa(aprendiz.getEmpresa());

        dto.setNit(aprendiz.getNit());
        dto.setPagado(aprendiz.getPagocurso());
        // Aquí deberás implementar la lógica para obtener el saldo
        dto.setSaldo(0.0);
        // Aquí deberás implementar la lógica para obtener el entrenador
        if (aprendiz.getPermisoTrabajoAlturas() == null || aprendiz.getPermisoTrabajoAlturas().getPersonaautoriza1() == null) {
            dto.setEntrenador("");
        } else {
            dto.setEntrenador(aprendiz.getPermisoTrabajoAlturas().getPersonaautoriza1().getNombrecompleto());
        }
        return dto;
    }
}
