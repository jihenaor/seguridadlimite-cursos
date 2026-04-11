#!/bin/sh
set -e
# Volumen Docker: /app/logs suele montarse como root; el proceso corre como appuser.
mkdir -p /app/logs
chown -R appuser:appgroup /app/logs

# Sin tope, la JVM puede reservar mucha RAM y el kernel mata el proceso (exit 137 = SIGKILL / OOM).
# Sobreescribe en docker-compose o .env, p. ej. JAVA_TOOL_OPTIONS="-Xms256m -Xmx768m"
if [ -z "${JAVA_TOOL_OPTIONS}" ]; then
  export JAVA_TOOL_OPTIONS="-Xms128m -Xmx512m"
fi

exec su-exec appuser java \
  -Djava.security.egd=file:/dev/./urandom \
  -Dspring.profiles.active=prod \
  -jar /app/app.jar
