# Almacenamiento de archivos en producción y copias de seguridad

Este documento describe **dónde** el backend Spring Boot guarda fotos de aprendices/trabajadores, firmas, documentos y certificados en el despliegue con Docker (`infraestructura/docker-compose.yml`), y **cómo** respaldarlos y restaurarlos.

## 1. Ubicación lógica en el contenedor

Dentro del contenedor `seguridad-backend`, las rutas por defecto (perfil `prod`, `application-prod.yml`) son:

| Tipo | Ruta en contenedor | Variable de entorno |
|------|--------------------|---------------------|
| Fotos de trabajador/aprendiz | `/app/storage/fotos/` | `PATH_FOTOS` |
| Firmas | `/app/storage/signatures/` | `PATH_SIGNATURES` |
| Documentos | `/app/storage/documents/` | `PATH_DOCUMENTS` |
| Certificados | `/app/storage/certificates/` | `PATH_CERTIFICADOS` |

Sobreescribir con variables de entorno en `docker-compose` o en el `.env` del servidor **no cambia** la convención de nombres de archivo en código; solo el directorio base.

### Convención de nombres (fotos)

- Archivo en disco: **`F{idTrabajador}.jpg`** (flujo actual: JPEG optimizado al guardar).
- Compatibilidad con datos antiguos: si existe **`F{id}.png`**, la lectura lo sigue considerando hasta que se vuelva a guardar una foto (migración a JPG).

Las rutas se resuelven al arranque del backend; en el log de arranque aparece una línea similar a:

`Rutas de almacenamiento (no Windows): fotos=/app/storage/fotos/, ...`

## 2. Persistencia en el servidor (Docker)

En `docker-compose.yml` el servicio `backend` declara:

- Volumen nombrado **`backend_storage`** montado en **`/app/storage`** del contenedor.

Ese volumen vive en el motor Docker del host (típicamente bajo `/var/lib/docker/volumes/`). **No forma parte de la imagen** del backend: sobrevive a actualizaciones de imagen siempre que no se elimine el volumen.

### Nombre real del volumen en el host

Docker antepone el **nombre del proyecto Compose** al nombre del volumen. El proyecto suele ser el nombre del directorio desde el que ejecutas `docker compose` (por ejemplo `infraestructura_backend_storage`).

Comprobar en el servidor:

```bash
docker volume ls | grep backend_storage
```

Anotar el nombre completo para la variable `BACKEND_STORAGE_VOLUME` usada por el script de backup (ver sección 4).

### Alternativa: bind-mount en el host

Si la política del servidor exige rutas visibles en el filesystem (por ejemplo `/srv/seguridad/storage`), se puede sustituir el volumen nombrado por un bind-mount en `docker-compose.yml` y seguir usando `PATH_FOTOS=/app/storage/fotos/` dentro del contenedor (el host monta la carpeta en `/app/storage`). En ese caso las copias pueden hacerse directamente desde el host con `rsync` o herramientas de backup de archivos.

## 3. Optimización de fotos (tamaño en disco)

Configurable en el backend con `app.storage.foto` o variables de entorno:

- `APP_FOTO_MAX_EDGE_PX` (por defecto `960`): lado máximo en píxeles; se escala manteniendo proporción.
- `APP_FOTO_JPEG_QUALITY` (por defecto `0.82`): calidad JPEG entre 0 y 1.

Detalle de implementación: código Java en `GuardarFotoTrabajadorService` y `FotoImageOptimizer`.

## 4. Copias de seguridad

### 4.1 Base de datos (ya implementado)

El servicio `backup` ejecuta semanalmente `backup/backup.sh`, que genera:

- `backup_YYYYMMDD_HHMMSS.sql.gz` dentro del volumen **`backup_data`** (montado en `/backups` en el contenedor de backup).

Retención por defecto: **30 días** (solo dumps `.sql.gz`).

### 4.2 Archivos en disco (fotos, firmas, etc.)

Los dumps de MySQL **no incluyen** los binarios bajo `/app/storage`. Para no perder fotos y demás archivos hay que respaldar el volumen **`backend_storage`** (o la carpeta del bind-mount).

El mismo `backup.sh` puede generar, **si está configurado**, un archivo adicional:

- `storage_YYYYMMDD_HHMMSS.tar.gz` — contenido completo de `/app/storage` (fotos, signatures, documents, certificates).

Requisito en `.env` del servidor:

```bash
# Nombre exacto del volumen Docker (ver sección 2)
BACKEND_STORAGE_VOLUME=infraestructura_backend_storage
```

Si `BACKEND_STORAGE_VOLUME` está vacío, el script **solo** hace backup de la base de datos y registra que omitió el backup de archivos.

Retención: los `storage_*.tar.gz` antiguos se eliminan con la misma ventana de **30 días** que los SQL.

### 4.3 Copia fuera del servidor (recomendado)

El volumen `backup_data` debería copiarse periódicamente a almacenamiento externo (NAS, S3, otro VPS) mediante políticas del equipo de operaciones (`rsync`, `rclone`, backup del proveedor cloud, etc.). Documentar en runbook interno la frecuencia y el responsable.

## 5. Restauración de archivos

### Base de datos

Ver `backup/restore.sh` y la documentación existente de restauración SQL.

### Volumen de archivos (`storage_*.tar.gz`)

1. Detener o pausar el backend si se requiere consistencia total (opcional pero más seguro).
2. Crear un volumen nuevo o vaciar el existente (solo en recuperación ante desastre).
3. Restaurar con un contenedor temporal, por ejemplo:

```bash
# Sustituir NOMBRE_VOLUMEN y el archivo .tar.gz
docker run --rm \
  -v NOMBRE_VOLUMEN:/data \
  -v /ruta/donde/esta/storage_XXXXXX.tar.gz:/backup.tar.gz:ro \
  alpine:3.19 \
  sh -c "cd /data && rm -rf ./* && tar xzf /backup.tar.gz"
```

4. Arrancar el backend y comprobar logs de rutas y una lectura de foto de prueba.

## 6. Checklist operativo (producción)

- [ ] `docker volume ls` confirma `*_backend_storage` y `*_backup_data`.
- [ ] `.env` incluye `BACKEND_STORAGE_VOLUME=...` si se desea backup de archivos en el cron semanal.
- [ ] Tras el primer despliegue, verificar que existan directorios bajo `/app/storage` tras la primera foto o documento.
- [ ] Copias del volumen `backup_data` a medio externo configuradas.
- [ ] Runbook: quién restaura SQL y quién restaura `storage_*.tar.gz` y en qué orden (típicamente: archivos primero o según incidente).

## 7. Referencias en el repositorio

| Recurso | Ruta |
|---------|------|
| Compose producción | `infraestructura/docker-compose.yml` |
| Script backup | `infraestructura/backup/backup.sh` |
| Variables ejemplo | `infraestructura/.env.example` |
| YAML producción backend | `back/src/main/resources/application-prod.yml` |
| Resolución de rutas | `back/.../GetPathFiles.java`, `StoragePathResolver.java` |

---

*Última revisión alineada con volumen `backend_storage`, variables `PATH_*` y backup opcional de archivos en `backup.sh`.*
