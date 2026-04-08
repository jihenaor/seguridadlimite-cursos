package com.seguridadlimite.models.permiso.application.service;

import com.seguridadlimite.models.chequeo.application.port.in.ConsultarGrupoChequeoYDetalleUseCase;
import com.seguridadlimite.models.chequeo.domain.GrupoChequeo;
import com.seguridadlimite.models.nivel.application.NivelUpdateDTO;
import com.seguridadlimite.models.nivel.domain.dto.DiaDto;
import com.seguridadlimite.models.permiso.domain.PermisoDetalleChequeo;
import com.seguridadlimite.models.permiso.domain.PermisoFechas;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.models.permiso.domain.port.RegistrarPermisosTrabajoPort;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrarPermisosTrabajoService implements RegistrarPermisosTrabajoPort {

    private final PermisoTrabajoAlturasPort permisoTrabajoAlturasPort;

    private final ConsultarGrupoChequeoYDetalleUseCase consultarGrupoChequeoUseCase;


    @Override
    @Transactional
    public void registrarPermisos(List<NivelUpdateDTO> nivelesDTO) {
        if (nivelesDTO == null || nivelesDTO.isEmpty()) {
            throw new BusinessException("La lista de niveles no puede estar vacía, debe selecciona por lo meno un nivel de la lista al actualizar");
        }
        List<GrupoChequeo> gruposChequeo = consultarGrupoChequeoUseCase.consultarTodos();

        if (gruposChequeo.isEmpty()) {
            throw new BusinessException("No hay grupos de chequeo disponibles");
        }

        for (NivelUpdateDTO nivelDTO : nivelesDTO) {
            validarNivelDTO(nivelDTO);
            
            PermisoTrabajoAlturas permiso = PermisoTrabajoAlturas.builder()
                    .idNivel(nivelDTO.getId())
                    .fechaInicio(nivelDTO.getFechadesde())
                    .validodesde(nivelDTO.getFechadesde())
                    .validohasta(nivelDTO.getFechahasta())
                    .cupoinicial(nivelDTO.getCupoInicial() == null ? 10 : nivelDTO.getCupoInicial() )
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
        List<PermisoDetalleChequeo> permisoDetalleChequeos = new ArrayList<>();

        for (GrupoChequeo grupoChequeo : gruposChequeo) {
            grupoChequeo.getDetalles().forEach(detalleChequeo -> {
                permisoDetalleChequeos.add(PermisoDetalleChequeo.builder()
                        .idGrupo(detalleChequeo.getIdGrupo())
                        .descripcion(detalleChequeo.getDescripcion())
                        .respuesta("")
                        .permisoTrabajoAlturas(permiso)
                        .build());
            });
        }

        permiso.setPermisoDetalleChequeos(permisoDetalleChequeos);
    }

    private void registrarPermisoFechas(PermisoTrabajoAlturas permiso, List<DiaDto> diasdiseno) {
        List<PermisoFechas> permisoFechasList = new ArrayList<>();
        
        for (DiaDto diaDto : diasdiseno) {
            if (diaDto.getFecha() != null && !diaDto.getFecha().isEmpty() && diaDto.getFecha().length() == 10) {
                PermisoFechas permisoFechas = PermisoFechas.builder()
                        .permisoTrabajoAlturas(permiso)
                        .fecha(diaDto.getFecha())
                        .contexto(diaDto.getContexto())
                        .build();
                permisoFechasList.add(permisoFechas);
            }
        }
        
        // Establecer la relación en la entidad principal
        permiso.setPermisoFechas(permisoFechasList);
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

        // Validar formato de fecha AAAA-MM-DD
        if (!nivelDTO.getFechadesde().matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new BusinessException("La fecha de inicio debe tener el formato AAAA-MM-DD");
        }
        if (!nivelDTO.getFechahasta().matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new BusinessException("La fecha fin debe tener el formato AAAA-MM-DD");
        }

        // Obtener fecha actual en formato AAAA-MM-DD
        String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // Validar que la fecha de inicio no sea menor a la fecha actual
        if (nivelDTO.getFechadesde().compareTo(fechaActual) < 0) {
            throw new BusinessException("La fecha de inicio no puede ser menor a la fecha actual");
        }

        // Calcular fecha máxima (2 meses después de la fecha actual)
        String fechaMaxima = LocalDate.now().plusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // Validar que la fecha fin no sea mayor a 2 meses después de la fecha actual
        if (nivelDTO.getFechahasta().compareTo(fechaMaxima) > 0) {
            throw new BusinessException("La fecha fin no puede ser mayor a 2 meses después de la fecha actual");
        }

        // Validar que la fecha fin no sea menor a la fecha de inicio
        if (nivelDTO.getFechahasta().compareTo(nivelDTO.getFechadesde()) < 0) {
            throw new BusinessException("La fecha fin no puede ser menor a la fecha de inicio");
        }

        //if (nivelDTO.getCupoInicial() == null || nivelDTO.getCupoInicial() < 0) {
        //     throw new BusinessException("El cupo inicial debe ser un número positivo");
        //}
    }
} 