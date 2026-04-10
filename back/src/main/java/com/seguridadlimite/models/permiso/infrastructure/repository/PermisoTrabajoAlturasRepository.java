package com.seguridadlimite.models.permiso.infrastructure.repository;

import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermisoTrabajoAlturasRepository extends JpaRepository<PermisoTrabajoAlturas, Integer> {
    Optional<PermisoTrabajoAlturas> findByCodigoministerio(String codigoministerio);
    List<PermisoTrabajoAlturas> findByIdNivel(Integer idNivel);
    List<PermisoTrabajoAlturas> findTop10ByOrderByIdPermisoDesc();

    @Query("SELECT p FROM PermisoTrabajoAlturas p " +
            "WHERE p.validodesde between ?1 and ?2 " +
            "ORDER BY p.idPermiso DESC")
    List<PermisoTrabajoAlturas> findByFechaInicioBetweenOrderByIdPermisoDesc(String fechaInicio, String fechaFinal);

    @Query("SELECT p FROM PermisoTrabajoAlturas p " +
            "WHERE :fecha >= p.validodesde " +
            "AND :fecha <= p.validohasta" +
            " AND p.idNivel = :idNivel " +
            "AND p.cupofinal >= (SELECT COUNT(a)" +
                                " FROM Aprendiz a" +
                                " WHERE a.permisoTrabajoAlturas.idPermiso = p.idPermiso)")
    List<PermisoTrabajoAlturas> findAvailablePermissionsByDate(
            @Param("fecha") String fecha,
            @Param("idNivel") long idNivel);

    @Query("SELECT p FROM PermisoTrabajoAlturas p " +
            "WHERE :fecha >= p.validodesde " +
            "AND :fecha <= p.validohasta" +
            " AND p.idNivel = :idNivel ")
    List<PermisoTrabajoAlturas> findByDateIdNivel(
            @Param("fecha") String fecha,
            @Param("idNivel") long idNivel);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PermisoTrabajoAlturas p " +
           "WHERE p.validodesde <= :fecha AND p.validohasta >= :fecha")
    boolean existsByFechaBetweenValidoDesdeAndValidoHasta(@Param("fecha") String fecha);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PermisoTrabajoAlturas p " +
            "WHERE p.validodesde <= :fecha AND p.validohasta >= :fecha")
    boolean existsByFechaBetweenValidoDesdeValidoHasta(@Param("fecha") String fecha);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PermisoTrabajoAlturas p " +
            "WHERE p.validodesde <= :fecha AND p.validohasta >= :fecha" +
            " AND p.idNivel = :idNivel")
    boolean existsByFechaBetweenValidoDesdeValidoHastaNivel(@Param("fecha") String fecha, @Param("idNivel") long idNivel);

    @Query("SELECT p FROM PermisoTrabajoAlturas p " +
            "WHERE :fecha >= p.validodesde " +
            "AND :fecha <= p.validohasta")
    List<PermisoTrabajoAlturas> findByDate(
            @Param("fecha") String fecha);

    @Query("SELECT DISTINCT p FROM PermisoTrabajoAlturas p " +
            "LEFT JOIN FETCH p.permisoFechas " +
            "WHERE :fecha >= p.validodesde " +
            "AND :fecha <= p.validohasta")
    List<PermisoTrabajoAlturas> findByDateWithPermisoFechas(
            @Param("fecha") String fecha);

    @Query("SELECT p FROM PermisoTrabajoAlturas p " +
            "WHERE :fechaInicio = p.fechaInicio " +
            "AND p.idNivel = :idNivel " +
            "AND p.idpersonaautoriza1 = :idpersonaautoriza1")
    Optional<PermisoTrabajoAlturas> findByFechaInicioNivelIdpersonaautoriza(
            @Param("fechaInicio") String fechaInicio,
            @Param("idNivel") Integer idNivel,
            @Param("idpersonaautoriza1") Integer idpersonaautoriza1);
} 