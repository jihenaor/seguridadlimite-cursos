#!/bin/bash
# ══════════════════════════════════════════════════════════════
#  generar-secretos.sh
#
#  Genera claves criptográficamente seguras para el archivo .env
#  Ejecútalo una sola vez al configurar un nuevo servidor o entorno.
#
#  Uso:
#    chmod +x generar-secretos.sh
#    ./generar-secretos.sh
# ══════════════════════════════════════════════════════════════
set -e

echo ""
echo "══════════════════════════════════════════════════════"
echo "  Generador de Secretos — Seguridad Al Límite"
echo "══════════════════════════════════════════════════════"
echo ""
echo "Copia estos valores en tu archivo .env:"
echo ""
echo "# JWT_SECRET (256 bits en Base64)"
echo "JWT_SECRET=$(openssl rand -base64 32)"
echo ""
echo "# DEPLOY_SECRET_TOKEN (64 chars hex)"
echo "DEPLOY_SECRET_TOKEN=$(openssl rand -hex 32)"
echo ""
echo "# MYSQL_ROOT_PASSWORD"
echo "MYSQL_ROOT_PASSWORD=$(openssl rand -base64 20 | tr -dc 'a-zA-Z0-9' | head -c 24)"
echo ""
echo "# DB_PASSWORD (usuario de aplicacion en .env: DB_USER=seguridadlimite_app)"
echo "DB_PASSWORD=$(openssl rand -base64 20 | tr -dc 'a-zA-Z0-9' | head -c 24)"
echo ""
echo "══════════════════════════════════════════════════════"
echo "  ¡IMPORTANTE! No compartas estos valores."
echo "  Guárdalos en un gestor de contraseñas."
echo "══════════════════════════════════════════════════════"
echo ""
