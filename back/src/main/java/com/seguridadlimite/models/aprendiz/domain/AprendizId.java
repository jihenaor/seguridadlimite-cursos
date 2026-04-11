package com.seguridadlimite.models.aprendiz.domain;

/**
 * La PK {@code sl_aprendices.id} es INT en MySQL; el dominio usa {@link Integer}.
 * Muchos controladores y DTOs siguen usando {@link Long}; aquí se centraliza la conversión.
 */
public final class AprendizId {

  private AprendizId() {}

  public static Integer toInteger(Long id) {
    return id == null ? null : id.intValue();
  }

  /** PK aprendiz desde {@code long} (p. ej. path o payload numérico). */
  public static Integer toInteger(long id) {
    return Math.toIntExact(id);
  }

  public static Long toLong(Integer id) {
    return id == null ? null : id.longValue();
  }
}
