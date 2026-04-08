#!/usr/bin/env bash
# Misma función que actualizar-usuario-mysql-docker.ps1; úsalo desde WSL.
# Uso:
#   ./scripts/actualizar-usuario-mysql-docker.sh
#   ./scripts/actualizar-usuario-mysql-docker.sh sst-mysql
#   ./scripts/actualizar-usuario-mysql-docker.sh sst-mysql 'clave_root_real'   # si .env está desactualizado
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
  # Quitar comillas externas como dotenv-java (MYSQL_ROOT_PASSWORD="clave" -> sin ")
  if [[ ${#v} -ge 2 ]]; then
    local f="${v:0:1}" l="${v: -1}"
    if [[ "$f" == '"' && "$l" == '"' ]] || [[ "$f" == "'" && "$l" == "'" ]]; then
      v="${v:1:${#v}-2}"
    fi
  fi
  printf '%s' "$v"
}

sql_escape() {
  printf '%s' "$1" | sed "s/'/''/g"
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
  echo "Falta MYSQL_ROOT_PASSWORD en .env o pasa la clave root como 2º argumento: $0 $CONTAINER 'clave_root'" >&2
  exit 1
fi

if [[ ! "$db_name" =~ ^[a-zA-Z0-9_]+$ ]]; then
  echo "DB_NAME debe ser identificador simple: ${db_name}" >&2
  exit 1
fi

u="$(sql_escape "$db_user")"
p="$(sql_escape "$db_pass")"
d="$db_name"

sql="CREATE USER IF NOT EXISTS '${u}'@'%' IDENTIFIED WITH mysql_native_password BY '${p}';
ALTER USER '${u}'@'%' IDENTIFIED WITH mysql_native_password BY '${p}';
GRANT ALL PRIVILEGES ON \`${d}\`.* TO '${u}'@'%';
FLUSH PRIVILEGES;"

tmp="$(mktemp)"
envf="$(mktemp)"
trap 'rm -f "$tmp" "$envf"' EXIT
printf '%s\n' "$sql" >"$tmp"
printf 'MYSQL_PWD=%s\n' "$root_pass" >"$envf"

if ! docker ps --format '{{.Names}}' | grep -qx "$CONTAINER"; then
  echo "No hay contenedor en ejecución llamado '${CONTAINER}'. Prueba: docker ps" >&2
  exit 1
fi

docker cp "$tmp" "${CONTAINER}:/tmp/fix-app-user.sql"
docker exec --env-file "$envf" "$CONTAINER" mysql -uroot -e "source /tmp/fix-app-user.sql"

echo "Usuario '${db_user}' actualizado en '${CONTAINER}'. Reinicia Spring."
