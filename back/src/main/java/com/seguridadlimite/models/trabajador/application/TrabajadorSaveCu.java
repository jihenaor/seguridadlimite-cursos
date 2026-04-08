package com.seguridadlimite.models.trabajador.application;

import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TrabajadorSaveCu {

    private final ITrabajadorDao dao;

    @Transactional
    public Trabajador save(Trabajador trabajador) {
        inicializarValoresDefecto(trabajador);

        return dao.save(trabajador);
    }

    private void inicializarValoresDefecto(Trabajador trabajador) {
        String defaultString = "";
        String nacionalidadDefault = "CO";
        String generoDefault = "M";
        String fotoDefault = "N";
        String validoDefault = "N";
        Date createAtDefault = new Date();

        trabajador.setNacionalidad(Objects.requireNonNullElse(trabajador.getNacionalidad(), nacionalidadDefault));
        trabajador.setPrimernombre(Objects.requireNonNullElse(trabajador.getPrimernombre(), defaultString).toUpperCase());
        trabajador.setSegundonombre(Objects.requireNonNullElse(trabajador.getSegundonombre(), defaultString).toUpperCase());
        trabajador.setPrimerapellido(Objects.requireNonNullElse(trabajador.getPrimerapellido(), defaultString).toUpperCase());
        trabajador.setSegundoapellido(Objects.requireNonNullElse(trabajador.getSegundoapellido(), defaultString).toUpperCase());
        trabajador.setGenero(Objects.requireNonNullElse(trabajador.getGenero(), generoDefault));
        trabajador.setNacionalidad(Objects.requireNonNullElse(trabajador.getNacionalidad(), nacionalidadDefault));
        trabajador.setTiposangre(Objects.requireNonNullElse(trabajador.getTiposangre(), defaultString));
        trabajador.setOcupacion(Objects.requireNonNullElse(trabajador.getOcupacion(), defaultString));

        trabajador.setCelular(Objects.requireNonNullElse(trabajador.getCelular(), defaultString));
        trabajador.setCorreoelectronico(Objects.requireNonNullElse(trabajador.getCorreoelectronico(), defaultString));
        trabajador.setFoto(Objects.requireNonNullElse(trabajador.getFoto(), fotoDefault));
        trabajador.setValido(Objects.requireNonNullElse(trabajador.getValido(), validoDefault));
        trabajador.setCreateAt(trabajador.getCreateAt() == null ? createAtDefault : trabajador.getCreateAt());
        trabajador.setAdjuntodocumento(Objects.requireNonNullElse(trabajador.getAdjuntodocumento(), validoDefault));

        trabajador.setUpdateAt(new Date());
    }
}
