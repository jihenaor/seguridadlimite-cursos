package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.models.chequeo.application.port.in.ConsultarGrupoChequeoYDetalleUseCase;
import com.seguridadlimite.models.chequeo.domain.GrupoChequeo;
import com.seguridadlimite.models.nivel.application.NivelUpdateDTO;
import com.seguridadlimite.models.nivel.domain.dto.DiaDto;
import com.seguridadlimite.models.permiso.application.dto.PermisoSolapamientoConflictoDTO;
import com.seguridadlimite.models.permiso.application.exception.PermisoSolapamientoException;
import com.seguridadlimite.models.permiso.domain.PermisoDetalleChequeo;
import com.seguridadlimite.models.permiso.domain.PermisoFechas;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.models.permiso.domain.port.RegistrarPermisosTrabajoPort;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegistrarPermisosTrabajoService implements RegistrarPermisosTrabajoPort {

    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;
    private final ConsultarGrupoChequeoYDetalleUseCase consultarGrupoChequeoUseCase;

    public RegistrarPermisosTrabajoService(
            PermisoTrabajoAlturasPort permisoTrabajoAlturasPort,
            ConsultarGrupoChequeoYDetalleUseCase consultarGrupoChequeoUseCase) {
        this.permisoTrabajoAlturasPort = permisoTrabajoAlturasPort;
        this.consultarGrupoChequeoUseCase = consultarGrupoChequeoUseCase;
    }

    @Override
    @Transactional
    public void registrarPermisos(List<NivelUpdateDTO> nivelesDTO, boolean forzarSolapamiento) {
        if (nivelesDTO == null || nivelesDTO.isEmpty()) {
            throw new BusinessException("La lista de niveles no puede estar vacía, debe selecciona por lo meno un nivel de la lista al actualizar");
        }
        List<GrupoChequeo> gruposChequeo = consultarGrupoChequeoUseCase.consultarTodos();

        if (gruposChequeo.isEmpty()) {
            throw new BusinessException("No hay grupos de chequeo disponibles");
        }

        for (NivelUpdateDTO nivelDTO : nivelesDTO) {
            validarNivelDTO(nivelDTO);
        }

        if (!forzarSolapamiento) {
            List<PermisoSolapamientoConflictoDTO> conflictos = new ArrayList<>();
            for (NivelUpdateDTO nivelDTO : nivelesDTO) {
                Integer idNivel = nivelDTO.getId().intValue();
                String fd = nivelDTO.getFechadesde().trim();
                String fh = nivelDTO.getFechahasta().trim();
                List<PermisoTrabajoAlturas> solapados =
                        permisoTrabajoAlturasPort.findByIdNivelSolapamientoRango(idNivel, fd, fh);
                for (PermisoTrabajoAlturas existente : solapados) {
                    conflictos.add(new PermisoSolapamientoConflictoDTO(
                            idNivel,
                            existente.getIdPermiso(),
                            existente.getValidodesde(),
                            existente.getValidohasta(),
                            fd,
                            fh));
                }
            }
            if (!conflictos.isEmpty()) {
                throw new PermisoSolapamientoException(
                        "Se detectó solapamiento con permisos ya registrados para el mismo nivel. "
                                + "Revise el detalle en «conflictos» o confirme si desea registrar un permiso adicional.",
                        conflictos);
            }
        }

        for (NivelUpdateDTO nivelDTO : nivelesDTO) {
            Integer idNivel = nivelDTO.getId().intValue();
            String fd = nivelDTO.getFechadesde().trim();
            String fh = nivelDTO.getFechahasta().trim();

            PermisoTrabajoAlturas permiso = PermisoTrabajoAlturas.builder()
                    .idNivel(idNivel)
                    .fechaInicio(fd)
                    .validodesde(fd)
                    .validohasta(fh)
                    .cupoinicial(nivelDTO.getCupoInicial() == null ? 10 : nivelDTO.getCupoInicial())
                    .cupofinal(10)
                    .numerogrupos(nivelDTO.getNumerogrupos() == null ? 1 : nivelDTO.getNumerogrupos())
                    .dias(nivelDTO.getDias() == null ? 1 : nivelDTO.getDias())
                    .build();

            registrarPermisoDetalleChequeo(permiso, gruposChequeo);
            registrarPermisoFechas(permiso, nivelDTO.getDiasdiseno());

            permisoTrabajoAlturasPort.save(permiso);
        }
    }

    private void registrarPermisoDetalleChequeo(PermisoTrabajoAlturas permiso, List<GrupoChequeo> gruposChequeo ) {
        if (permiso.getPermisoDetalleChequeos() == null) {
            permiso.setPermisoDetalleChequeos(new ArrayList<>());
        } else {
            permiso.getPermisoDetalleChequeos().clear();
        }

        for (GrupoChequeo grupoChequeo : gruposChequeo) {
            grupoChequeo.getDetalles().forEach(detalleChequeo ->
                    permiso.getPermisoDetalleChequeos().add(PermisoDetalleChequeo.builder()
                            .idGrupo(detalleChequeo.getIdGrupo())
                            .descripcion(detalleChequeo.getDescripcion())
                            .respuesta("")
                            .permisoTrabajoAlturas(permiso)
                            .build()));
        }
    }

    private void registrarPermisoFechas(PermisoTrabajoAlturas permiso, List<DiaDto> diasdiseno) {
        if (diasdiseno == null || diasdiseno.isEmpty()) {
            permiso.setPermisoFechas(new ArrayList<>());
            return;
        }

        // Una fila por fecha calendario: en BD suele existir UNIQUE idx_fecha_permiso (id_permiso, fecha).
        // El front puede enviar la misma fecha repetida (distinto dia/contexto/unidad); sin esto falla el INSERT.
        Map<String, DiaDto> unicosPorFecha = new LinkedHashMap<>();
        for (DiaDto diaDto : diasdiseno) {
            String fecha = normalizarFechaCalendario(diaDto.getFecha());
            if (fecha == null) {
                continue;
            }
            unicosPorFecha.putIfAbsent(fecha, diaDto);
        }

        if (permiso.getPermisoFechas() == null) {
            permiso.setPermisoFechas(new ArrayList<>());
        } else {
            permiso.getPermisoFechas().clear();
        }

        for (DiaDto diaDto : unicosPorFecha.values()) {
            String fecha = normalizarFechaCalendario(diaDto.getFecha());
            permiso.getPermisoFechas().add(PermisoFechas.builder()
                    .permisoTrabajoAlturas(permiso)
                    .fecha(fecha)
                    .dia(diaDto.getDia())
                    .contexto(diaDto.getContexto())
                    .unidad(diaDto.getUnidad())
                    .build());
        }
    }

    /** yyyy-MM-dd normalizado; null si no es una fecha calendario válida de 10 caracteres. */
    private static String normalizarFechaCalendario(String raw) {
        if (raw == null) {
            return null;
        }
        String t = raw.trim();
        if (t.length() != 10 || !t.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return null;
        }
        return t;
    }

    private void validarNivelDTO(NivelUpdateDTO nivelDTO) {
        if (nivelDTO.getId() == null) {
            throw new BusinessException("El ID del nivel es obligatorio");
        }
        if (nivelDTO.getFechadesde() == null || nivelDTO.getFechadesde().trim().isEmpty()) {
            throw new BusinessException("La fecha de inicio es obligatoria");
        }
        if (nivelDTO.getFechahasta() == null || nivelDTO.getFechahasta().trim().isEmpty()) {
            throw new BusinessException("La fecha fin es obligatoria");
        }

        String fd = nivelDTO.getFechadesde().trim();
        String fh = nivelDTO.getFechahasta().trim();
        nivelDTO.setFechadesde(fd);
        nivelDTO.setFechahasta(fh);

        if (!fd.matches("\\d{4}-\\d{2}-\\d{2}") || !fh.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new BusinessException("Las fechas deben tener el formato AAAA-MM-DD");
        }

        LocalDate dDesde = parseFechaIso(fd, "fecha de inicio");
        LocalDate dHasta = parseFechaIso(fh, "fecha fin");
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusMonths(2);

        if (dDesde.isBefore(hoy)) {
            throw new BusinessException("La fecha de inicio no puede ser anterior a la fecha actual");
        }
        if (dHasta.isBefore(hoy)) {
            throw new BusinessException("La fecha fin no puede ser anterior a la fecha actual");
        }
        if (dDesde.isAfter(limite)) {
            throw new BusinessException("La fecha de inicio no puede ser posterior a 2 meses desde la fecha actual");
        }
        if (dHasta.isAfter(limite)) {
            throw new BusinessException("La fecha fin no puede ser posterior a 2 meses desde la fecha actual");
        }
        if (dDesde.isAfter(dHasta)) {
            throw new BusinessException("La fecha de inicio no puede ser posterior a la fecha fin");
        }

        validarFechasDiasDiseno(nivelDTO.getDiasdiseno(), dDesde, dHasta, hoy, limite);
    }

    private static LocalDate parseFechaIso(String valor, String etiqueta) {
        try {
            return LocalDate.parse(valor, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new BusinessException("La " + etiqueta + " no es una fecha válida del calendario (AAAA-MM-DD)");
        }
    }

    /**
     * Fechas digitadas en el diseño: dentro de [hoy, hoy+2 meses] y dentro del rango del permiso [inicio, fin].
     */
    private void validarFechasDiasDiseno(
            List<DiaDto> diasdiseno,
            LocalDate dDesde,
            LocalDate dHasta,
            LocalDate hoy,
            LocalDate limite) {
        if (diasdiseno == null || diasdiseno.isEmpty()) {
            return;
        }
        for (int i = 0; i < diasdiseno.size(); i++) {
            DiaDto diaDto = diasdiseno.get(i);
            if (diaDto.getFecha() == null || diaDto.getFecha().trim().isEmpty()) {
                continue;
            }
            String f = normalizarFechaCalendario(diaDto.getFecha());
            if (f == null) {
                throw new BusinessException(
                        "La fecha del día de diseño (posición " + (i + 1) + ") debe ser AAAA-MM-DD válida");
            }
            LocalDate d = parseFechaIso(f, "fecha del día de diseño");
            if (d.isBefore(hoy)) {
                throw new BusinessException("Las fechas de diseño no pueden ser anteriores a la fecha actual");
            }
            if (d.isAfter(limite)) {
                throw new BusinessException("Las fechas de diseño no pueden ser posteriores a 2 meses desde la fecha actual");
            }
            if (d.isBefore(dDesde) || d.isAfter(dHasta)) {
                throw new BusinessException(
                        "Las fechas de diseño deben estar entre la fecha de inicio y la fecha fin del permiso");
            }
            if (diaDto.getDia() == null) {
                throw new BusinessException(
                        "El día del cronograma es obligatorio para cada fecha de diseño (posición " + (i + 1) + ")");
            }
        }
    }
} 