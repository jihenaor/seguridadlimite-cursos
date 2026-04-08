package com.seguridadlimite.models.parametros.infraestructure;

import com.seguridadlimite.models.parametros.dominio.Parametros;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface IParametrosDao extends CrudRepository<Parametros, String>{

    @Query(value = "SELECT NOW() FROM dual", nativeQuery = true)
    Date obtenerFechaActual();
}
