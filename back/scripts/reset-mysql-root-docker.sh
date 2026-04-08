#!/usr/bin/env bash
# Restablece la contraseña de root de MySQL en un contenedor Docker (volumen existente).
# Usa mysqld --skip-grant-tables (solo entorno local / confía en tu máquina).
#
# Uso:
#   bash scripts/reset-mysql-root-docker.sh sst-mysql
#   bash scripts/reset-mysql-root-docker.sh sst-mysql 'MiNuevaClaveSegura'
#
# Después: actualiza MYSQL_ROOT_PASSWORD en back/.env y, si aplica, scripts/actualizar-usuario-mysql-docker.*
set -euo pipefail

CONTAINER="${1:?Uso: $0 <nombre_contenedor> [nueva_contraseña]}"
NEW_PASS="${2:-}"

if [[ -z "$NEW_PASS" ]]; then
  read -r -s -p "Nueva contraseña para root MySQL: " NEW_PASS
  echo
  read -r -s -p "Repite la contraseña: " NEW_PASS2
  echo
  if [[ "$NEW_PASS" != "$NEW_PASS2" ]]; then
    echo "Las contraseñas no coinciden." >&2
    exit 1
  fi
fi

if [[ -z "$NEW_PASS" ]]; then
  echo "La contraseña no puede estar vacía." >&2
  exit 1
fi

if ! docker inspect "$CONTAINER" &>/dev/null; then
  echo "No existe el contenedor: $CONTAINER" >&2
  exit 1
fi

MT_LINE=$(docker inspect "$CONTAINER" --format '{{range .Mounts}}{{if eq .Destination "/var/lib/mysql"}}{{printf "%s|%s|%s" .Type .Name .Source}}{{"\n"}}{{end}}{{end}}' 2>/dev/null | head -n1 || true)
if [[ -z "$MT_LINE" ]]; then
  echo "No se encontró montaje en /var/lib/mysql para $CONTAINER. ¿Es un contenedor MySQL oficial?" >&2
  exit 1
fi

IFS='|' read -r MOUNT_TYPE VOL_NAME SRC_PATH <<<"$MT_LINE"

if [[ "$MOUNT_TYPE" == "volume" && -n "$VOL_NAME" ]]; then
  VOL_SPEC="${VOL_NAME}:/var/lib/mysql"
elif [[ "$MOUNT_TYPE" == "bind" && -n "$SRC_PATH" ]]; then
  VOL_SPEC="${SRC_PATH}:/var/lib/mysql"
else
  echo "Montaje no soportado (tipo=$MOUNT_TYPE). Línea: $MT_LINE" >&2
  exit 1
fi

IMG=$(docker inspect "$CONTAINER" --format '{{.Config.Image}}')
TEMP="mysql-root-reset-$$-$RANDOM"
RUNNING=0
if docker inspect "$CONTAINER" --format '{{.State.Running}}' | grep -q true; then
  RUNNING=1
fi

echo ">>> Parando contenedor $CONTAINER (si estaba en marcha)..."
docker stop "$CONTAINER" &>/dev/null || true

echo ">>> Arrancando contenedor temporal con --skip-grant-tables (imagen: $IMG)..."
docker run -d --name "$TEMP" \
  -v "$VOL_SPEC" \
  "$IMG" \
  mysqld --default-authentication-plugin=mysql_native_password --skip-grant-tables --skip-networking

cleanup() {
  [[ -n "${TEMP:-}" ]] || return 0
  echo ">>> Eliminando contenedor temporal $TEMP..."
  docker stop "$TEMP" &>/dev/null || true
  docker rm "$TEMP" &>/dev/null || true
}
trap cleanup EXIT INT TERM

echo ">>> Esperando a mysqld..."
for _ in $(seq 1 45); do
  if docker exec "$TEMP" mysqladmin ping -uroot --silent 2>/dev/null; then
    break
  fi
  sleep 1
done

if ! docker exec "$TEMP" mysqladmin ping -uroot --silent 2>/dev/null; then
  echo "MySQL no respondió a tiempo en el contenedor temporal." >&2
  exit 1
fi

echo ">>> Aplicando nueva contraseña de root..."
# Con --skip-grant-tables, MySQL 8 rechaza ALTER USER (error 1290). Se actualiza mysql.user con
# el hash mysql_native_password. El SQL se escribe en un fichero para no romper si la clave tiene $ o '.
RESET_SQL=$(mktemp)
{
  echo "USE mysql;"
  printf "UPDATE user SET authentication_string = CONCAT('*', UPPER(SHA1(UNHEX(SHA1('"
  printf '%s' "$NEW_PASS" | sed "s/'/''/g"
  printf "'))))), plugin = 'mysql_native_password', password_expired = 'N' WHERE User = 'root';\n"
  echo "FLUSH PRIVILEGES;"
} >"$RESET_SQL"
docker cp "$RESET_SQL" "$TEMP:/tmp/reset_root.sql"
docker exec "$TEMP" mysql -uroot -e "source /tmp/reset_root.sql"
rm -f "$RESET_SQL"

cleanup
trap - EXIT

echo ">>> Arrancando de nuevo $CONTAINER..."
docker start "$CONTAINER"

echo ">>> Esperando mysqld en $CONTAINER..."
for _ in $(seq 1 45); do
  if docker exec -e MYSQL_PWD="$NEW_PASS" "$CONTAINER" mysql -uroot -e "SELECT 1" &>/dev/null; then
    break
  fi
  sleep 1
done

if docker exec -e MYSQL_PWD="$NEW_PASS" "$CONTAINER" mysql -uroot -e "SELECT 1" &>/dev/null; then
  echo ">>> Verificación OK: root acepta la nueva contraseña en $CONTAINER."
else
  echo "Advertencia: no se pudo verificar root en $CONTAINER; comprueba manualmente con MYSQL_PWD." >&2
fi

if [[ "$RUNNING" -eq 0 ]]; then
  echo "Nota: el contenedor estaba parado; ahora está iniciado. Para volver a pararlo: docker stop $CONTAINER"
fi

echo ""
echo "Listo. Pon la misma clave en back/.env como MYSQL_ROOT_PASSWORD y ejecuta actualizar-usuario-mysql-docker si hace falta."
