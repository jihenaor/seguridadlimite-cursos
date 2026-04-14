package com.seguridadlimite.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Optional;

public class DateUtil {

    /**
     * Zona horaria del negocio (inscripciones, plazos, evaluaciones).
     * Evita desfases de un día cuando el JVM del servidor está en UTC (p. ej. Docker)
     * y la fecha límite o “hoy” deben alinearse con el calendario en Colombia.
     */
    public static final ZoneId ZONA_COLOMBIA = ZoneId.of("America/Bogota");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String dateToString(Date date, String formato) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                Objects.isNull(formato) ? "yyyy-MM-dd" : formato);
        formatter.setTimeZone(TimeZone.getTimeZone(ZONA_COLOMBIA));
        return formatter.format(date);
    }

    public static String toDayInString() {
        return LocalDate.now(ZONA_COLOMBIA).format(DATE_FORMATTER);
    }

    /**
     * Fecha/hora al inicio del día calendario en Colombia, desplazada {@code days} días desde hoy (Bogotá).
     * Usado p. ej. para {@code fechalimiteinscripcion} = mañana en calendario local CO.
     */
    public static java.util.Date dateInDate(Optional<Integer> days) {
        return java.util.Date.from(
                LocalDate.now(ZONA_COLOMBIA)
                        .plusDays(days.orElse(0))
                        .atStartOfDay(ZONA_COLOMBIA)
                        .toInstant());
    }

    public static int compararFechas(Date fechaActual, Date fechaComparar) {
        return fechaActual.compareTo(fechaComparar);
    }

    public static int compararFechas(Date fechaActual, String fechaComparar) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = dateFormat.parse(fechaComparar);

        return fechaActual.compareTo(date);
    }

    public static Map<String, String> getDateComponents(Date date) {
        Map<String, String> dateComponents = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Los meses se indexan desde 0, así que sumamos 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DecimalFormat df = new DecimalFormat("00"); // Formato para completar con ceros

        dateComponents.put("Year", df.format(year));
        dateComponents.put("Month", df.format(month));
        dateComponents.put("Day", df.format(day));

        return dateComponents;
    }

    public static Date stringToDate(String fechaString)  {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatoFecha.parse(fechaString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDate stringToLocalDate(String fechaString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(fechaString, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Error al parsear la fecha: " + fechaString, e);
        }
    }

    public static String getCurrentDate() {
        return LocalDate.now(ZONA_COLOMBIA).format(DATE_FORMATTER);
    }


    public static int calcularEdad(String fechaNacimiento) {
        try {
            if (fechaNacimiento == null || fechaNacimiento.trim().isEmpty()) {
                throw new com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException(
                    "La fecha de nacimiento no puede ser nula o vacía");
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaNacimientoLocalDate = LocalDate.parse(fechaNacimiento.trim(), formatter);
            
            // Validar que la fecha no sea futura (calendario Colombia)
            if (fechaNacimientoLocalDate.isAfter(LocalDate.now(ZONA_COLOMBIA))) {
                throw new com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException(
                    "La fecha de nacimiento no puede ser una fecha futura: " + fechaNacimiento);
            }
            
            return Period.between(fechaNacimientoLocalDate, LocalDate.now(ZONA_COLOMBIA)).getYears();
            
        } catch (DateTimeParseException e) {
            throw new com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException(
                "Error al procesar la fecha de nacimiento '" + fechaNacimiento + "'. " +
                "El formato esperado es AAAA-MM-dd (ejemplo: 1990-12-25). Error: " + e.getMessage());
        } catch (com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException e) {
            // Re-lanzar BusinessException sin modificar
            throw e;
        } catch (Exception e) {
            throw new com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException(
                "Error inesperado al calcular la edad para la fecha '" + fechaNacimiento + "': " + e.getMessage());
        }
    }
}
