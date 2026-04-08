#!/bin/bash
# ══════════════════════════════════════════════════════════════
#  start-local.sh — Levanta el stack completo en modo desarrollo
#
#  Pre-requisitos:
#    1. Docker Desktop corriendo
#    2. Archivo .env configurado (cp .env.example .env) con DB_USER / DB_PASSWORD
#       (usuario de aplicación; no uses root para la API)
#    3. Código de back y microfrontends disponible
#
#  URLs disponibles después de iniciar:
#    http://localhost       → Gateway (enruta a los microfrontends)
#    http://localhost:4200  → Portal Trabajador (directo)
#    http://localhost:4201  → Panel Admin       (directo)
#    http://localhost:8090  → Backend API       (Postman/debug)
#    http://localhost:8080  → phpMyAdmin        (MySQL)
# ══════════════════════════════════════════════════════════════

set -e

# Verificar que existe el archivo .env
if [ ! -f ".env" ]; then
    echo ""
    echo "❌  No se encontró el archivo .env"
    echo "    Copia el ejemplo y configura los valores:"
    echo "    cp .env.example .env && nano .env"
    echo ""
    exit 1
fi

# Usar gateway-local.conf (sin TLS)
export NGINX_CONF=./nginx/gateway-local.conf

echo ""
echo "🚀  Levantando stack local con microfrontends..."
echo ""

# Construir imágenes y levantar servicios
docker compose \
    -f docker-compose.yml \
    -f docker-compose.local.yml \
    --env-file .env \
    up --build -d

echo ""
echo "✅  Stack levantado correctamente."
echo ""
echo "   Portal Trabajador → http://localhost:4200"
echo "   Panel Admin       → http://localhost:4201"
echo "   API Backend       → http://localhost:8090/cursosback"
echo "   Gateway           → http://localhost"
echo "   phpMyAdmin        → http://localhost:8080"
echo ""
echo "   Para ver logs: docker compose logs -f"
echo "   Para detener:  docker compose down"
echo ""
