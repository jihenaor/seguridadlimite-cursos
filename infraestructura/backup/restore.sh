#!/bin/bash

set -e

if [ -z "$1" ]; then
    echo "Uso: $0 <archivo_backup.sql.gz> [nombre_base_datos]"
    echo "Ejemplo: $0 backup_20240101_120000.sql.gz"
    exit 1
fi

BACKUP_FILE="$1"
DB_NAME="${2:-wwsegu_cursos}"
DB_USER="${DB_USER:-seguridadlimite_app}"
DB_PASSWORD="${DB_PASSWORD}"
DB_HOST="${DB_HOST:-mysql}"

log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*"
}

if [ ! -f "$BACKUP_FILE" ]; then
    log "ERROR: Archivo no encontrado: $BACKUP_FILE"
    exit 1
fi

log "Restaurando $BACKUP_FILE en base de datos $DB_NAME"

gunzip -c "$BACKUP_FILE" | docker exec -i seguridad-mysql mysql \
    -u"$DB_USER" \
    -p"$DB_PASSWORD" \
    -h"$DB_HOST" \
    "$DB_NAME"

log "Restauración completada exitosamente"