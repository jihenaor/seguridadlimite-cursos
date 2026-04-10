#!/bin/bash

set -e

BACKUP_DIR="/backups"
DB_NAME="${DB_NAME:-wwsegu_cursos}"
DB_USER="${DB_USER:-seguridadlimite_app}"
DB_PASSWORD="${DB_PASSWORD}"
DB_HOST="${DB_HOST:-mysql}"
RETENTION_DAYS=30
# Volumen Docker del backend con /app/storage (fotos, firmas, documentos, certificados).
# Obtener nombre: docker volume ls | grep backend_storage
BACKEND_STORAGE_VOLUME="${BACKEND_STORAGE_VOLUME:-}"

timestamp() {
    date +"%Y%m%d_%H%M%S"
}

log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*"
}

log "Iniciando backup de $DB_NAME"

mkdir -p "$BACKUP_DIR"

backup_file="$BACKUP_DIR/backup_$(timestamp).sql.gz"

docker exec seguridad-mysql mysqldump \
    -u"$DB_USER" \
    -p"$DB_PASSWORD" \
    -h"$DB_HOST" \
    --single-transaction \
    --quick \
    --lock-tables=false \
    "$DB_NAME" | gzip > "$backup_file"

if [ -f "$backup_file" ]; then
    size=$(du -h "$backup_file" | cut -f1)
    log "Backup creado: $backup_file ($size)"

    find "$BACKUP_DIR" -name "backup_*.sql.gz" -mtime +$RETENTION_DAYS -delete
    log "Backups SQL antiguos eliminados (retención: $RETENTION_DAYS días)"
else
    log "ERROR: Backup falló"
    exit 1
fi

# ── Archivos en /app/storage (fotos, firmas, etc.) — requiere BACKEND_STORAGE_VOLUME en .env
if [ -n "$BACKEND_STORAGE_VOLUME" ]; then
    storage_file="$BACKUP_DIR/storage_$(timestamp).tar.gz"
    log "Iniciando backup de volumen de archivos: $BACKEND_STORAGE_VOLUME"
    if docker volume inspect "$BACKEND_STORAGE_VOLUME" >/dev/null 2>&1; then
        docker run --rm \
            -v "$BACKEND_STORAGE_VOLUME:/data:ro" \
            -v "$BACKUP_DIR:/out" \
            alpine:3.19 \
            tar czf "/out/$(basename "$storage_file")" -C /data .
        if [ -f "$storage_file" ]; then
            sz=$(du -h "$storage_file" | cut -f1)
            log "Backup de archivos creado: $storage_file ($sz)"
        else
            log "ERROR: No se generó el tar de almacenamiento"
            exit 1
        fi
        find "$BACKUP_DIR" -name "storage_*.tar.gz" -mtime +$RETENTION_DAYS -delete
        log "Backups storage_*.tar.gz antiguos eliminados (retención: $RETENTION_DAYS días)"
    else
        log "ADVERTENCIA: volumen Docker no encontrado: $BACKEND_STORAGE_VOLUME (omitiendo backup de archivos)"
    fi
else
    log "Omitiendo backup de archivos en disco: defina BACKEND_STORAGE_VOLUME en .env (ver docs/almacenamiento-y-backup-archivos.md)"
fi