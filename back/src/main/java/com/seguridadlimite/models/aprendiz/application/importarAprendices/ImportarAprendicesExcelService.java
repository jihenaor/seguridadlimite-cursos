package com.seguridadlimite.models.aprendiz.application.importarAprendices;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.models.trabajador.application.TrabajadorFindByDocumentoCu;
import com.seguridadlimite.models.trabajador.application.TrabajadorSaveCu;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import com.seguridadlimite.springboot.backend.apirest.services.AprendizServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportarAprendicesExcelService {

    private static final String SHEET_NAME = "2020-2021-2022-2023-2024-2025";

    private static final Map<String, Long> NIVEL_ID_MAP;

    static {
        NIVEL_ID_MAP = new HashMap<>();
        NIVEL_ID_MAP.put("TRABAJADOR AUTORIZADO", 1L);
        NIVEL_ID_MAP.put("COORDINADOR", 2L);
        NIVEL_ID_MAP.put("COORDINADORES", 2L);
        NIVEL_ID_MAP.put("BASICO OPERATIVO", 3L);
        NIVEL_ID_MAP.put("REENTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("REENTRENAMENTO", 4L);
        NIVEL_ID_MAP.put("RENNTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("REEENTRENAMIENTO", 4L);
        NIVEL_ID_MAP.put("REENTRENAMIENO", 4L);
        NIVEL_ID_MAP.put("REENTRENO", 4L);
        NIVEL_ID_MAP.put("ADMINISTRATIVO", 5L);
        NIVEL_ID_MAP.put("ADMINSTRATIVO", 5L);
        NIVEL_ID_MAP.put("BASICO ADMINISTRATIVO", 5L);
        NIVEL_ID_MAP.put("ADMINISTRATIVO PERSONAL", 9L);
        NIVEL_ID_MAP.put("AVANZADO", 7L);
        NIVEL_ID_MAP.put("AVANAZADO", 7L);
        NIVEL_ID_MAP.put("AVAZANDO", 7L);
        NIVEL_ID_MAP.put("ACTUALIZACION DE COORDINADOR", 8L);
    }

    private final AprendizServiceImpl aprendizService;
    private final TrabajadorFindByDocumentoCu trabajadorFindByDocumento;
    private final TrabajadorSaveCu trabajadorSaveCu;
    private final IAprendizDao aprendizDao;
    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;
    private final ConsultarPermisoTrabajoAlturasUseCase consultarPermisoTrabajoAlturasUseCase;

    public ImportResultado importar(MultipartFile file) {
        ImportResultado resultado = new ImportResultado();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                resultado.setError("No se encontró la hoja '" + SHEET_NAME + "' en el archivo Excel.");
                return resultado;
            }

            // Buscar la fila de encabezados dinámicamente (puede haber filas de título antes)
            int headerRowIndex = encontrarFilaEncabezados(sheet);
            if (headerRowIndex < 0) {
                resultado.setError("No se encontró la fila de encabezados (se esperaba CEDULA o NIVEL).");
                return resultado;
            }
            Row headerRow = sheet.getRow(headerRowIndex);
            Map<String, Integer> columnIndex = mapearColumnas(headerRow);
            log.info("Encabezados en fila {}: {}", headerRowIndex + 1, columnIndex.keySet());

            // Cargar aprendices y permisos en memoria para deduplicación eficiente
            List<String> documentNumbers = extraerDocumentos(sheet, columnIndex, headerRowIndex + 1);
            List<Aprendiz> aprendicesExistentes = documentNumbers.isEmpty()
                    ? Collections.emptyList()
                    : aprendizService.findByNumeroDocumentoIn(documentNumbers);

            List<PermisoTrabajoAlturas> permisos = permisoTrabajoAlturasPort.findAll().stream()
                    .filter(p -> p.getCodigoministerio() != null)
                    .toList();

            int creados = 0;
            int omitidos = 0;
            int errores = 0;

            // Empezar desde la fila inmediatamente después de los encabezados
            for (int i = headerRowIndex + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    Registro registro = leerFila(row, columnIndex);

                    if (registro.getCedula() == null || registro.getCedula().length() < 3) continue;
                    if (registro.getNivel() == null || registro.getNivel().isBlank()) continue;

                    Long idNivel = NIVEL_ID_MAP.get(registro.getNivel().trim().toUpperCase());
                    if (idNivel == null) {
                        log.warn("Fila {}: nivel desconocido '{}'", i + 1, registro.getNivel());
                        errores++;
                        continue;
                    }

                    String cedula = normalizarNumeroDocumento(registro.getCedula());
                    if (cedula.length() < 3) {
                        continue;
                    }

                    // Verificar si ya existe aprendiz con misma cédula, nivel y código
                    boolean yaExiste = aprendicesExistentes.stream()
                            .anyMatch(a -> cedula.equals(a.getTrabajador().getNumerodocumento())
                                    && a.getIdnivel() == idNivel.intValue()
                                    && registro.getCodigo() != null
                                    && registro.getCodigo().equals(a.getCodigoverificacion()));

                    if (yaExiste) {
                        omitidos++;
                        continue;
                    }

                    // Buscar el permiso de trabajo por código ministerio
                    Optional<PermisoTrabajoAlturas> permiso = Optional.empty();
                    if (registro.getTrabajo() != null && !registro.getTrabajo().isBlank()) {
                        permiso = permisos.stream()
                                .filter(p -> p.getCodigoministerio().equals(registro.getTrabajo()))
                                .findFirst();
                    }

                    if (crearAprendiz(registro, idNivel, permiso)) {
                        creados++;
                    } else {
                        omitidos++;
                    }

                } catch (Exception e) {
                    log.error("Error procesando fila {}: {}", i + 1, e.getMessage());
                    errores++;
                }
            }

            resultado.setCreados(creados);
            resultado.setOmitidos(omitidos);
            resultado.setErrores(errores);
            log.info("Importación finalizada — creados: {}, omitidos: {}, errores: {}", creados, omitidos, errores);

        } catch (Exception e) {
            log.error("Error al procesar el archivo Excel", e);
            resultado.setError("Error al procesar el archivo: " + e.getMessage());
        }

        return resultado;
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    /**
     * Busca la fila que contiene los encabezados buscando "CEDULA" o "NIVEL"
     * en cualquiera de sus celdas. Soporta archivos con filas de título previas.
     */
    private int encontrarFilaEncabezados(Sheet sheet) {
        for (int i = 0; i <= Math.min(sheet.getLastRowNum(), 20); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            for (Cell cell : row) {
                String valor = getCellString(cell).trim().toUpperCase();
                if ("CEDULA".equals(valor) || "NIVEL".equals(valor) || "CÓDIGO".equals(valor)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private Map<String, Integer> mapearColumnas(Row headerRow) {
        Map<String, Integer> map = new HashMap<>();
        for (Cell cell : headerRow) {
            String nombre = getCellString(cell).trim().toUpperCase();
            if (!nombre.isEmpty()) {
                map.put(nombre, cell.getColumnIndex());
            }
        }
        return map;
    }

    private List<String> extraerDocumentos(Sheet sheet, Map<String, Integer> columnIndex, int startRow) {
        Integer cedulaIdx = resolverIdx(columnIndex, "CEDULA", "NÚMERO DOCUMENTO", "NUMERODOCUMENTO");
        if (cedulaIdx == null) return Collections.emptyList();

        List<String> docs = new ArrayList<>();
        for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Cell cell = row.getCell(cedulaIdx);
            if (cell != null) {
                String val = getCellString(cell).trim();
                if (val.length() >= 3) docs.add(val);
            }
        }
        return docs;
    }

    private Registro leerFila(Row row, Map<String, Integer> columnIndex) {
        Registro r = new Registro();
        r.setTrabajo(getColValue(row, columnIndex, "ID MIN. TRABAJO", "TRABAJO", "IDMINTRABAJO"));
        r.setCodigo(getColValue(row, columnIndex, "CODIGO", "CÓDIGO"));
        r.setFechaInicial(getColValue(row, columnIndex, "FECHA INICIAL", "FECHAINICIAL"));
        r.setFecha(getColValue(row, columnIndex, "FECHA"));
        r.setNombre(getColValue(row, columnIndex, "NOMBRE"));
        r.setCedula(limpiarCedula(getColValue(row, columnIndex, "CEDULA", "NÚMERO DOCUMENTO", "NUMERODOCUMENTO")));
        r.setNivel(getColValue(row, columnIndex, "NIVEL"));
        r.setEnfasis(getColValue(row, columnIndex, "ENFASIS", "ÉNFASIS"));
        r.setContacto(getColValue(row, columnIndex, "# CONTACTO", "CONTACTO"));
        r.setEmpresa(getColValue(row, columnIndex, "EMPRESA", "NOMBREEMPRESA"));
        r.setNit(getColValue(row, columnIndex, "NIT"));
        r.setPagado(getColValue(row, columnIndex, "PAGADO"));
        r.setSaldo(getColValue(row, columnIndex, "SALDO"));
        r.setEntrenador(getColValue(row, columnIndex, "ENTRENADOR"));
        return r;
    }

    private String limpiarCedula(String valor) {
        if (valor == null) return null;
        return valor.replace(".", "").replace(",", "").trim();
    }

    /**
     * Crea el aprendiz asociado al trabajador. Si no existe trabajador por número de documento, crea uno mínimo
     * desde los datos de la fila (nombre, contacto) usando {@link TrabajadorSaveCu}.
     *
     * @return {@code true} si se persistió el aprendiz; {@code false} si no hubo documento válido para buscar/crear.
     */
    private boolean crearAprendiz(Registro registro, long idNivel, Optional<PermisoTrabajoAlturas> permiso) {
        String numeroDocumento = normalizarNumeroDocumento(registro.getCedula());
        if (numeroDocumento.isEmpty()) {
            log.warn("Cédula vacía tras normalizar; fila omitida.");
            return false;
        }

        Trabajador trabajador = trabajadorFindByDocumento.findByNumerodocumento(numeroDocumento);
        if (trabajador == null) {
            log.info("Trabajador no encontrado con documento {}; se crea desde Excel.", numeroDocumento);
            trabajador = trabajadorSaveCu.save(construirTrabajadorParaImportacion(registro, numeroDocumento));
        }

        Aprendiz aprendiz = new Aprendiz();
        aprendiz.setIdtrabajador(trabajador.getId() == null ? null : trabajador.getId().intValue());
        aprendiz.setTrabajador(trabajador);
        aprendiz.setIdnivel(Math.toIntExact(idNivel));
        aprendiz.setCodigoverificacion(registro.getCodigo());
        aprendiz.setIdenfasis(0);
        aprendiz.setCreateAt(new Date());
        aprendiz.setUpdateAt(new Date());

        // Datos empresa
        aprendiz.setEmpresa(registro.getEmpresa() != null ? registro.getEmpresa() : "");
        aprendiz.setNit(registro.getNit() != null ? registro.getNit() : "");

        // Pago
        aprendiz.setPagocurso(resolverPago(registro.getPagado()));

        // Permiso de trabajo (opcional)
        permiso.ifPresent(p -> aprendiz.setIdPermiso(p.getIdPermiso()));

        // Domicilio por defecto
        aprendiz.setDepartamentodomicilio("ARMENIA");
        aprendiz.setCiudaddomicilio("QUINDIO");
        aprendiz.setDirecciondomicilio("");

        // Los demás campos NOT NULL son completados por AprendizServiceImpl.save()
        aprendizService.save(aprendiz);
        return true;
    }

    private static String normalizarNumeroDocumento(String cedula) {
        if (cedula == null) {
            return "";
        }
        String d = cedula.trim();
        if (d.length() > 16) {
            return d.substring(0, 16);
        }
        return d;
    }

    /**
     * Trabajador nuevo para importación: documento, tipo CC, nombre desde columna NOMBRE (o marcador IMPORTADO),
     * celular desde contacto si cabe en 10 caracteres. El resto lo completa {@link TrabajadorSaveCu}.
     */
    private Trabajador construirTrabajadorParaImportacion(Registro registro, String numeroDocumento) {
        Trabajador t = new Trabajador();
        t.setTipodocumento("CC");
        t.setNumerodocumento(numeroDocumento);
        rellenarNombreTrabajadorDesdeRegistro(t, registro.getNombre());
        if (registro.getContacto() != null && !registro.getContacto().isBlank()) {
            String c = registro.getContacto().replaceAll("\\D", "");
            t.setCelular(c.length() <= 10 ? c : c.substring(0, 10));
        }
        return t;
    }

    private void rellenarNombreTrabajadorDesdeRegistro(Trabajador t, String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            t.setPrimernombre("IMPORTADO");
            t.setSegundonombre("");
            t.setPrimerapellido("");
            t.setSegundoapellido("");
            return;
        }
        String[] p = nombreCompleto.trim().split("\\s+");
        if (p.length == 1) {
            t.setPrimernombre(truncarCampoNombre(p[0], 60));
            t.setSegundonombre("");
            t.setPrimerapellido("");
            t.setSegundoapellido("");
        } else if (p.length == 2) {
            t.setPrimernombre(truncarCampoNombre(p[0], 60));
            t.setSegundonombre("");
            t.setPrimerapellido(truncarCampoNombre(p[1], 20));
            t.setSegundoapellido("");
        } else if (p.length == 3) {
            t.setPrimernombre(truncarCampoNombre(p[0], 60));
            t.setSegundonombre("");
            t.setPrimerapellido(truncarCampoNombre(p[1], 20));
            t.setSegundoapellido(truncarCampoNombre(p[2], 20));
        } else {
            t.setPrimernombre(truncarCampoNombre(p[0], 60));
            t.setSegundonombre(truncarCampoNombre(p[1], 20));
            t.setPrimerapellido(truncarCampoNombre(p[p.length - 2], 20));
            t.setSegundoapellido(truncarCampoNombre(p[p.length - 1], 20));
        }
    }

    private static String truncarCampoNombre(String parte, int maxLen) {
        if (parte == null || parte.isEmpty()) {
            return parte;
        }
        return parte.length() <= maxLen ? parte : parte.substring(0, maxLen);
    }

    private String resolverPago(String pagado) {
        if (pagado == null || pagado.isBlank()) return "N";
        return switch (pagado.trim().toUpperCase()) {
            case "CANCELADO", "CANCELO", "CANCEO", "PAGO" -> "S";
            case "DEBE" -> "N";
            case "DONACION" -> "D";
            case "OBSEQUIO" -> "O";
            default -> pagado.length() >= 1 ? pagado.substring(0, 1) : "N";
        };
    }

    // ── Utilidades de lectura de celda ────────────────────────────────────────

    private String getColValue(Row row, Map<String, Integer> columnIndex, String... nombres) {
        Integer idx = resolverIdx(columnIndex, nombres);
        if (idx == null) return null;
        Cell cell = row.getCell(idx);
        return cell == null ? null : getCellString(cell).trim();
    }

    private Integer resolverIdx(Map<String, Integer> columnIndex, String... nombres) {
        for (String nombre : nombres) {
            Integer idx = columnIndex.get(nombre.toUpperCase());
            if (idx != null) return idx;
        }
        return null;
    }

    private String getCellString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield new java.text.SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
                }
                double val = cell.getNumericCellValue();
                // Evitar notación científica en cédulas numéricas
                yield val == Math.floor(val) ? String.valueOf((long) val) : String.valueOf(val);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCachedFormulaResultType() == CellType.NUMERIC
                    ? String.valueOf((long) cell.getNumericCellValue())
                    : cell.getStringCellValue();
            default -> "";
        };
    }

    // ── DTO resultado ────────────────────────────────────────────────────────

    public static class ImportResultado {
        private int creados;
        private int omitidos;
        private int errores;
        private String error;

        public int getCreados() { return creados; }
        public void setCreados(int creados) { this.creados = creados; }
        public int getOmitidos() { return omitidos; }
        public void setOmitidos(int omitidos) { this.omitidos = omitidos; }
        public int getErrores() { return errores; }
        public void setErrores(int errores) { this.errores = errores; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        @Override
        public String toString() {
            return error != null ? "Error: " + error
                    : String.format("Creados: %d | Omitidos: %d | Errores: %d", creados, omitidos, errores);
        }
    }
}
