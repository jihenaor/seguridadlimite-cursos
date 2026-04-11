package com.seguridadlimite.models.asistencia.infrastructure.persistence;

import com.seguridadlimite.models.asistencia.domain.Asistencia;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAsistenciaDao extends CrudRepository<Asistencia, Long> {
  @Query("select u from Asistencia u where u.idaprendiz = ?1 order by u.modulo, u.dia, u.unidad")
  List<Asistencia> findByIdaprendiz(int idaprendiz);

  @Query("select u from Asistencia u where u.fecha = ?1 and u.idaprendiz = ?2")
  Asistencia findByIdhorarioIdaprendiz(String fecha, Long idaprendiz);

  @Modifying
  @Query("Update Asistencia u set u.fecha = ?1 where u.id = ?2")
  int updateFecha(String fecha, int id);

  @Query("select count(u) from Asistencia u where u.idaprendiz = ?1")
  Long countByIdaprendiz(int idaprendiz);

  @Query("select count(u) from Asistencia u where u.idaprendiz = ?1 and u.fecha is null")
  int countByIdaprendizAndFechaIsNull(int idaprendiz);

  List<Asistencia> findByIdaprendizIn(List<Long> aprendizIds);

  @Query("select distinct idaprendiz from Asistencia u where u.fecha between ?1 and ?2")
  List<Long> findIdAprendizByFecha(String fechadesde, String fechahasta);
}
