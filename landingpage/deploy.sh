#!/bin/bash

echo "🔄 Iniciando proceso de despliegue..."

# Cargar NVM
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # Esto carga nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # Esto carga la completación de nvm

# Cambiar a Node.js 14.17.0
echo "📦 Cambiando a Node.js v20.10.0..."
nvm use v20.10.0

if [ $? -ne 0 ]; then
    echo "❌ Error: No se pudo cambiar a Node.js 14.17.0"
    exit 1
fi

# Actualizar código desde git
echo "🔄 Actualizando código desde git..."
git pull

if [ $? -ne 0 ]; then
    echo "❌ Error: Fallo al actualizar el código"
    exit 1
fi

# Construir la aplicación
echo "🏗️ Construyendo la aplicación..."
npm run build

if [ $? -ne 0 ]; then
    echo "❌ Error: Fallo en la construcción"
    exit 1
fi

# Verificar si existe el directorio build
if [ ! -d "build" ]; then
    echo "❌ Error: No se encontró el directorio build"
    exit 1
fi

# Entrar al directorio build
echo "📂 Entrando al directorio build..."
cd build

# Copiar archivos al directorio de producción
echo "📂 Copiando archivos a producción..."
sudo cp -r ./* /var/www/html/landingpage/

# Volver al directorio anterior
cd ..

# Configurar permisos correctos
echo "🔒 Configurando permisos..."
sudo chown -R www-data:www-data /var/www/html/landingpage
sudo chmod -R 755 /var/www/html/landingpage

if [ $? -ne 0 ]; then
    echo "❌ Error: No se pudieron copiar los archivos o configurar permisos"
    exit 1
fi

echo "✅ Despliegue completado exitosamente!" 