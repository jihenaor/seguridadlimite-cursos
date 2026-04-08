package com.seguridadlimite.models.aprendiz.infraestructure;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IAprendizDao extends CrudRepository<Aprendiz, Long>{
  @Query("select u from Aprendiz u where u.trabajador.numerodocumento = :numerodocumento")
  List<Aprendiz> findBynumerodocumento(@Param("numerodocumento") String numerodocumento);

  @Query("select u from Aprendiz u " +
          "where u.codigoverificacion is null " +
          "or u.codigoverificacion like 'E%' " +
          "or u.codigoverificacion like 'F%' " +
          "or u.codigoverificacion like 'G%' " +
          "order by u.codigoverificacion")
  List<Aprendiz> findByCodigoVerificacion();

  @Query("select u from Aprendiz u where u.codigoverificacion is null")
  List<Aprendiz> findByCodigoVerificacionIsnull();

  @Query("select u from Aprendiz u where u.trabajador.numerodocumento = ?1 " +
          " and (u.codigoverificacion is null " +
          "          or u.codigoverificacion like 'E%' " +
          "          or u.codigoverificacion like 'F%' " +
          "          or u.codigoverificacion like 'G%')")
  List<Aprendiz> findByTrabajadorNumerodocumento(String numerodocumento);

  @Query("select u from Aprendiz u where u.trabajador.numerodocumento in ?1 and (u.codigoverificacion is null or u.codigoverificacion like 'E%' or u.codigoverificacion like 'F%' or u.codigoverificacion like 'G%')")
  List<Aprendiz> findByTrabajadorNumerodocumentoIn(List<String> numerosDocumento);

  @Query("select u " +
          "from Aprendiz u " +
          "where u.trabajador.numerodocumento = ?1 ")
  Aprendiz findBynumerodocumentoGrupoActivo(String numerodocumento);
  
  @Query("select u from Aprendiz u where u.trabajador.id = ?1")
  List<Aprendiz> findByIdtrabajador(Long idtrabajador);
  /*
  @Query("select u " +
          "from Aprendiz u where u.trabajador.numerodocumento = ?1")
  Aprendiz findBynumerodocumentogrupo(String numerodocumento, Long idgrupo);
*/

  @Modifying
  @Query("UPDATE Aprendiz c SET c.codigoverificacion = :codigoverificacion WHERE c.id = :id")
  int updateCodigoverificacion(@Param("codigoverificacion") String codigoverificacion, @Param("id") Long id);
  
  @Modifying
  @Query("UPDATE Aprendiz c SET c.eteorica1 = :nota WHERE c.id = :idaprendiz")
  int updateEvaluacionteorica1(@Param("nota") Double nota, @Param("idaprendiz") Long idaprendiz);
  
  @Modifying
  @Query("UPDATE Aprendiz c SET c.eteorica2 = :nota WHERE c.id = :idaprendiz")
  int updateEvaluacionteorica2(@Param("nota") Double nota, @Param("idaprendiz") Long idaprendiz);

  @Modifying
  @Query("UPDATE Aprendiz c SET c.eenfasis = :nota WHERE c.id = :idaprendiz")
  int updateEvaluacionteoricaEnfasis(@Param("nota") Double nota, @Param("idaprendiz") Long idaprendiz);

  @Modifying
  @Query("UPDATE Aprendiz c SET c.epractica = :nota WHERE c.id = :idaprendiz")
  int updateEvaluacionpractica(@Param("nota") Double nota,
                               @Param("idaprendiz") Long idaprendiz);

  @Modifying
  @Query("UPDATE Aprendiz c SET c.eingreso = :nota WHERE c.id = :idaprendiz")
  int updateEvaluacionIngreso(@Param("nota") Double nota,
                              @Param("idaprendiz") Long idaprendiz);

  @Modifying
  @Query("UPDATE Aprendiz c SET c.exteteorica = :exteteorica WHERE c.id = :idaprendiz")
  int updateExteteorica(@Param("exteteorica") String exteteorica, @Param("idaprendiz") Long idaprendiz);

  @Modifying
  @Query("UPDATE Aprendiz c SET c.extepractica = :extepractica WHERE c.id = :idaprendiz")
  int updateExtepractica(@Param("extepractica") String extepractica, @Param("idaprendiz") Long idaprendiz);
  
  @Modifying
  @Query("UPDATE Aprendiz c SET c.intentos = c.intentos + 1 WHERE c.id = ?1 and c.intentos < 2")
  int updateIntentos(@Param("idaprendiz") Long idaprendiz);
  
  @Modifying
  @Query("UPDATE Aprendiz c SET c.intentos = 0 WHERE c.id = ?1")
  int reiniciarIntentos(@Param("idaprendiz") Long idaprendiz);

  @Query("""
    select u from Aprendiz u 
    where u.trabajador.id = :idtrabajador 
      and u.fechainscripcion >= :fechaInicio 
    order by u.fechainscripcion desc
""")
  List<Aprendiz> findUltimoInscritoByTrabajadorId(
          @Param("fechaInicio") String String,
          @Param("idtrabajador") Long idtrabajador,
          Pageable pageable);

  @Query("select count(u) from Aprendiz u")
  int contarAprendices(Long idgrupo);

  @Query("select u from Aprendiz u" +
          " where u.trabajador.numerodocumento = :numerodocumento" +
          " and u.fechalimiteinscripcion >= NOW()")
  List<Aprendiz> findApredizDocumentoFechaLimiteInscripcion(@Param("numerodocumento") String numerodocumento);

  @Query("select a from Aprendiz a " +
          " WHERE a.trabajador.numerodocumento = ?1" +
          " and EXISTS (" +
          " SELECT 1" +
          "        FROM PermisoTrabajoAlturas p" +
          "        WHERE a.fechaUltimaAsistencia BETWEEN p.validodesde AND p.validohasta and p.validodesde <= ?2 AND p.validohasta >= ?2" +
          "        and a.idnivel = p.idNivel )" +
          " ORDER BY a.id DESC")
  List<Aprendiz> findFirstEvaluacionActiva(String numerodocumento, String fecha);

  @Query("select case when count(u) > 0 then true else false end " +
         "from Aprendiz u " +
         "where EXISTS (" +
         "  SELECT 1 FROM PermisoTrabajoAlturas p " +
         "  WHERE u.idPermiso = p.idPermiso " +
         "  AND :fecha BETWEEN p.validodesde AND p.validohasta" +
         ")")
  boolean existsByFechaBetweenValidodesdeAndValidohasta(@Param("fecha") String fecha);

  @Query("select u from Aprendiz u" +
          " where u.trabajador.numerodocumento = :numerodocumento" +
          " and u.fechalimiteinscripcion >= :fechaInicio")
  Aprendiz findByIdtrabajadorEncuestaActiva(@Param("fechaInicio") Date fechaInicio,  @Param("numerodocumento") String numerodocumento);
/*
  @Query("select u " +
          "from Aprendiz u " +
          "where u.trabajador.id = ?1 and grupo.id = ?2")
  Aprendiz findByIdtrabajadorIdgrupo(Long idtrabajador, Long idgrupo);
*/
  /*
  @Query("select u"
    + " from Aprendiz u "
    + "where u.trabajador.id = ?1 and u.grupo.aucodestad = 'A'")
  List<Aprendiz> findByIdtrabajadorGrupoActivo(Long idtrabajador);
*/
  /*
  @Query("select u "
          + "from Aprendiz u "
          + "where u.idtrabajador = ?1 "
          + "and u.grupo.id = ?2")
  List<Aprendiz> findByIdtrabajador(Long idtrabajador, Long idgrupo);
  */

/*
  @Query("select u"
    + " from Aprendiz u "
    + "where u.empresa.id = ?1")
  List<Aprendiz> findByIdempresa(Long idempresa);
*/
  @Query("SELECT u FROM Aprendiz u "
               + "WHERE CONCAT(u.trabajador.primernombre, ' ', COALESCE(u.trabajador.segundonombre, ''), ' ', "
               + "u.trabajador.primerapellido, ' ', COALESCE(u.trabajador.segundoapellido, '')) LIKE %:searchTerm%")
  List<Aprendiz> findByNombre(@Param("searchTerm") String searchTerm);

  /*
  @Query("select u"
     		+ " from Aprendiz u "
     		+ "where u.empresa.id = ?1 and u.grupo.aucodestad = 'A'")
  List<Aprendiz> findByIdempresaActivos(Long idempresa);
*/
  @Modifying
  @Query("UPDATE Aprendiz c SET c.fechaencuesta = :fechaencuesta " +
          " WHERE c.id = :idaprendiz")
  int updateFechaEncuesta(@Param("fechaencuesta") String fechaencuesta,
                          @Param("idaprendiz") Long idaprendiz);

  @Query("""
    SELECT a
    FROM Aprendiz a
    WHERE CAST(a.fechainscripcion AS string) LIKE %:fechaParametro% or CAST(a.fechaUltimaAsistencia AS string) LIKE %:fechaParametro%
  """)
    List<Aprendiz> findByFechaInscripcion(@Param("fechaParametro") String fechaParametro);

  @Query("""
    SELECT a
    FROM Aprendiz a
    WHERE EXISTS (
        SELECT 1
        FROM PermisoTrabajoAlturas p
        WHERE a.fechaUltimaAsistencia BETWEEN p.validodesde AND p.validohasta
          and a.idnivel = p.idNivel
          and :fechaActual >= p.validodesde and :fechaActual <= p.validohasta
    )
  """)
  List<Aprendiz> findByFechaActivo(@Param("fechaActual") String fechaActual);

  @Modifying
  @Query("UPDATE Aprendiz c SET c.fechalimiteevaluacion = :fechalimiteevaluacion" +
          " WHERE c.fechaUltimaAsistencia = :fechainscripcion")
  int updateFechaLimiteEvaluacion(@Param("fechainscripcion") String fechainscripcion,
                          @Param("fechalimiteevaluacion") Date fechalimiteevaluacion);

  @Modifying
  @Query("UPDATE Aprendiz c SET c.fechalimiteencuesta = :fechalimiteencuesta" +
          " WHERE fechainscripcion = :fechainscripcion")
  int updateFechaLimiteEncuesta(@Param("fechainscripcion") LocalDate fechainscripcion,
                                  @Param("fechalimiteencuesta") Date fechalimiteencuesta);

  @Query("select u" +
          " from Aprendiz u" +
          " where u.fechainscripcion = ?1 and u.trabajador.numerodocumento = ?2")
  Aprendiz findFechaInscripcionNumeroDocumento(LocalDate fecha, String numeroDocumento);

  @Query("SELECT a " +
          "FROM Aprendiz a " +
           "WHERE a.pagocurso = 'N' ")
  List<Aprendiz> findAprendicesPendientesPago();

  @Modifying
  @Query("UPDATE Aprendiz c SET c.pagocurso = :pagocurso WHERE c.id = :id")
  int updatePagoCurso(@Param("pagocurso") String pagocurso, @Param("id") Long id);
/*
  @Query("select u" +
          " from Aprendiz u" +
          " where u.grupo.fechainicio >= ?1 and u.grupo.fechainicio <= ?2")
  List<Aprendiz> findAprendicesFechaCurso(Date fechaInicial, Date fechaFinal);
*/
  @Query("select u" +
          " from Aprendiz u" +
          " where u.fechainscripcion >= :fechaInicial and u.fechainscripcion <= :fechaFinal")
  List<Aprendiz> findAprendicesFechaInscripcion(
          @Param("fechaInicial") String fechaInicial,
          @Param("fechaFinal") String fechaFinal);

  @Query("select u " +
          "from Aprendiz u " +
          "where u.fechaUltimaAsistencia >= :fechaInicial and u.fechaUltimaAsistencia <= :fechaFinal")
  List<Aprendiz> findAprendicesFechaUltimaAsistencia(
          @Param("fechaInicial") LocalDate fechaInicial,
          @Param("fechaFinal") LocalDate fechaFinal);

  @Query("select u " +
          "from Aprendiz u " +
          "where u.fechaUltimaAsistencia >= :fechaInicial " +
          "and u.fechaUltimaAsistencia <= :fechaFinal " +
          "and u.idnivel = :idnivel")
  List<Aprendiz> findAprendicesFechaUltimaAsistenciaIdNivel(
          @Param("fechaInicial") String fechaInicial,
          @Param("fechaFinal") String fechaFinal,
          @Param("idnivel") long idnivel);

  @Modifying
  @Query("UPDATE Aprendiz c SET c.fechaUltimaAsistencia = :fechaUltimaAsistencia WHERE c.id = :id")
  int updatefechaUltimaAsistencia(@Param("fechaUltimaAsistencia") String fechaUltimaAsistencia,
                                  @Param("id") Long id);

  @Modifying
  @Query("UPDATE Aprendiz c SET c.comentariosencuesta = :comentariosencuesta WHERE c.id = :id")
  int updateComentariosencuesta(@Param("comentariosencuesta") String cometariosencuesta, @Param("id") Long id);

  @Query("select u" +
          " from Aprendiz u" +
          " where u.id in :ids and u.idnivel = :idnivel")
  List<Aprendiz> findAprendicesByIds(
          @Param("ids") List<Long> ids,
          @Param("idnivel") Long idnivel);


  @Query("select u from Aprendiz u where u.nit = :nit")
  List<Aprendiz> findByNitCertificado(@Param("nit") String nit);


  @Modifying
  @Query("UPDATE Aprendiz c SET c.idPermiso = :idpermiso WHERE c.id = :id")
  int updatePermisotrabajo(@Param("idpermiso") int idpermiso, @Param("id") Long id);

  @Query("select u from Aprendiz u where u.idPermiso = :idpermiso")
  List<Aprendiz> findByIdPermiso(@Param("idpermiso") int idpermiso);

  @Query("select u " +
          "from Aprendiz u " +
          "where u.id in (select idaprendiz" +
              " from Asistencia" +
              " where fecha between :validodesde and :validohasta)" +
              " and idnivel = :idnivel")
  List<Aprendiz> findAprendicesAsistieronRangoFechas(
          @Param("validodesde") String validodesde,
          @Param("validohasta") String validohasta,
          @Param("idnivel") long idnivel
  );


  @Query("select u " +
            "from Aprendiz u " +
            "where u.codigoverificacion = :codigoverificacion " +
            "and u.trabajador.numerodocumento = :numerodocumento " +
            "and u.idnivel = :idnivel")
    List<Aprendiz> findPorCodigoverifiacion(
            @Param("codigoverificacion") String codigoverificacion,
            @Param("numerodocumento") String numerodocumento,
            @Param("idnivel") long idnivel
    );

    @Query("select sabeleerescribir " +
            "from Aprendiz u " +
            "where u.id = :id")
    String consultarAprendizSabeLeerEscribir(@Param("id") long id);
}
