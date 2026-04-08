#!/bin/bash

set -e

BACKUP_DIR="/backups"
DB_NAME="${DB_NAME:-wwsegu_cursos}"
DB_USER="${DB_USER:-seguridadlimite_app}"
DB_PASSWORD="${DB_PASSWORD}"
DB_HOST="${DB_HOST:-mysql}"
RETENTION_DAYS=30

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
    log "Backup antigos eliminados (retention: $RETENTION_DAYS días)"
else
    log "ERROR: Backup falló"
    exit 1
fi