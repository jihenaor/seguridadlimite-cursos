package com.seguridadlimite.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Directorios de almacenamiento en disco usando {@link java.nio.file}: el mismo código sirve en
 * Windows, Linux y macOS porque {@link java.nio.file.FileSystems#getDefault()} aplica las reglas del SO
 * (separadores, rutas absolutas, etc.). No se bifurca manualmente por {@code os.name}.
 */
public final class StorageDirectories {

  private StorageDirectories() {}

  /** Une base + nombre de archivo de forma portable ({@link Path#resolve}). */
  public static Path resolveUnder(String baseDirectory, String fileName) {
    return Paths.get(baseDirectory).resolve(fileName).normalize();
  }

  /** Crea la jerarquía del directorio padre del archivo si aún no existe. */
  public static void ensureParentExists(Path filePath) throws IOException {
    Path parent = filePath.getParent();
    if (parent != null) {
      Files.createDirectories(parent);
    }
  }

  /**
   * Crea el directorio padre si falta, escribe el archivo y reintenta una vez si el fallo parece de
   * ruta inexistente (p. ej. carrera al crear el directorio, mensajes de Windows/Linux).
   */
  public static void writeBytes(Path target, byte[] data) throws IOException {
    ensureParentExists(target);
    try {
      Files.write(target, data);
    } catch (IOException first) {
      if (!isRetryableMissingPathOrParent(first)) {
        throw first;
      }
      ensureParentExists(target);
      Files.write(target, data);
    }
  }

  private static boolean isRetryableMissingPathOrParent(IOException e) {
    if (e instanceof NoSuchFileException || e instanceof FileNotFoundException) {
      return true;
    }
    Throwable c = e.getCause();
    if (c instanceof NoSuchFileException || c instanceof FileNotFoundException) {
      return true;
    }
    String msg = e.getMessage();
    if (msg == null) {
      return false;
    }
    String m = msg.toLowerCase();
    return m.contains("no such file")
        || m.contains("cannot find the path")
        || m.contains("el sistema no puede encontrar la ruta")
        || m.contains("el sistema no puede encontrar el archivo");
  }
}
