#!/bin/sh
set -e
# Volumen Docker: /app/logs suele montarse como root; el proceso corre como appuser.
mkdir -p /app/logs
chown -R appuser:appgroup /app/logs
exec su-exec appuser java \
  -Djava.security.egd=file:/dev/./urandom \
  -Dspring.profiles.active=prod \
  -jar /app/app.jar
