#!/bin/bash
# Solo se ejecuta la primera vez que el volumen de datos está vacío (docker-entrypoint-initdb.d).
# Refuerza mysql_native_password para MYSQL_USER@% según las variables del contenedor.
set -euo pipefail
mysql --protocol=socket -uroot -p"${MYSQL_ROOT_PASSWORD}" <<-EOSQL
	ALTER USER '${MYSQL_USER}'@'%' IDENTIFIED WITH mysql_native_password BY '${MYSQL_PASSWORD}';
	FLUSH PRIVILEGES;
EOSQL
