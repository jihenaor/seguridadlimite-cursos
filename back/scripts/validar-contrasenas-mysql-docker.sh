#!/usr/bin/env bash
# Comprueba root y usuario de app contra MySQL en Docker (sin modificar nada).
# Uso:
#   ./scripts/validar-contrasenas-mysql-docker.sh
#   ./scripts/validar-contrasenas-mysql-docker.sh sst-mysql
#   ./scripts/validar-contrasenas-mysql-docker.sh sst-mysql 'clave_root_si_no_esta_en_env'
set -euo pipefail

CONTAINER="${1:-mysql8}"
ROOT_OVERRIDE="${2:-}"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACK_ROOT="$(dirname "$SCRIPT_DIR")"
ENV_FILE="${BACK_ROOT}/.env"

if [[ ! -f "$ENV_FILE" ]]; then
  echo "No existe ${ENV_FILE}" >&2
  exit 1
fi

get_env() {
  local key="$1"
  local v
  v=$(grep -E "^[[:space:]]*${key}=" "$ENV_FILE" | head -1 | sed "s/^[[:space:]]*${key}=//" | tr -d '\r' | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')
  if [[ ${#v} -ge 2 ]]; then
    local f="${v:0:1}" l="${v: -1}"
    if [[ "$f" == '"' && "$l" == '"' ]] || [[ "$f" == "'" && "$l" == "'" ]]; then
      v="${v:1:${#v}-2}"
    fi
  fi
  printf '%s' "$v"
}

db_user="$(get_env DB_USER)"
db_name="$(get_env DB_NAME)"
db_pass="$(get_env DB_PASSWORD)"

if [[ -n "$ROOT_OVERRIDE" ]]; then
  root_pass="$ROOT_OVERRIDE"
else
  root_pass="$(get_env MYSQL_ROOT_PASSWORD)"
fi

if [[ -z "$db_user" || -z "$db_name" || -z "$db_pass" ]]; then
  echo "Faltan DB_USER, DB_NAME o DB_PASSWORD en .env" >&2
  exit 1
fi

if [[ -z "$root_pass" ]]; then
  echo "Falta MYSQL_ROOT_PASSWORD en .env o pasa la clave root como 2º argumento." >&2
  exit 1
fi

if [[ ! "$db_name" =~ ^[a-zA-Z0-9_]+$ ]]; then
  echo "DB_NAME inválido: ${db_name}" >&2
  exit 1
fi

if ! docker ps --format '{{.Names}}' | grep -qx "$CONTAINER"; then
  echo "No hay contenedor en ejecución: ${CONTAINER}" >&2
  exit 1
fi

envf="$(mktemp)"
trap 'rm -f "$envf"' EXIT

echo "Contenedor: ${CONTAINER}"

echo "Comprobando root..."
printf 'MYSQL_PWD=%s\n' "$root_pass" >"$envf"
if docker exec --env-file "$envf" "$CONTAINER" mysql -uroot -e "SELECT 1 AS root_ok" >/dev/null 2>&1; then
  echo "  Root MySQL: OK"
  root_ok=1
else
  echo "  Root MySQL: FALLA" >&2
  root_ok=0
fi

echo "Comprobando usuario de aplicación (${db_user}) y base ${db_name}..."
printf 'MYSQL_PWD=%s\n' "$db_pass" >"$envf"
if docker exec --env-file "$envf" "$CONTAINER" mysql -u"$db_user" -D"$db_name" -e "SELECT 1 AS app_ok" >/dev/null 2>&1; then
  echo "  Usuario app + base: OK"
  app_ok=1
else
  echo "  Usuario app + base: FALLA (típico 1045). Si root OK: ./scripts/actualizar-usuario-mysql-docker.sh $CONTAINER" >&2
  app_ok=0
fi

if [[ "$root_ok" -eq 1 && "$app_ok" -eq 1 ]]; then
  echo "Todas las comprobaciones pasaron."
  exit 0
fi
exit 1
