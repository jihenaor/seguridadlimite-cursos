#!/bin/sh
set -e
# Volumen Docker: /app/logs suele montarse como root; el proceso corre como appuser.
mkdir -p /app/logs
chown -R appuser:appgroup /app/logs

# Mismo caso para PATH_* (prod): el volumen backend_storage en /app/storage nace como root:root;
# sin esto, appuser recibe AccessDeniedException al crear firmas/fotos/documentos/certificados.
mkdir -p /app/storage/fotos /app/storage/signatures /app/storage/documents /app/storage/certificates
chown -R appuser:appgroup /app/storage

# Sin tope, la JVM puede reservar mucha RAM y el kernel mata el proceso (exit 137 = SIGKILL / OOM).
# Sobreescribe en docker-compose o .env, p. ej. JAVA_TOOL_OPTIONS="-Xms256m -Xmx768m"
if [ -z "${JAVA_TOOL_OPTIONS}" ]; then
  export JAVA_TOOL_OPTIONS="-Xms128m -Xmx512m"
fi

exec su-exec appuser java \
  -Djava.security.egd=file:/dev/./urandom \
  -Dspring.profiles.active=prod \
  -jar /app/app.jar
