#!/bin/bash

# Restablecer el archivo deploy.sh a su versión en el repositorio para evitar conflictos
echo "Restableciendo deploy.sh a su versión en el repositorio..."
git checkout -- deploy.sh || { echo "Error al restablecer deploy.sh"; exit 1; }

# Actualizar el repositorio local con los últimos cambios
echo "Ejecutando git pull para actualizar el código..."
git pull || { echo "Error al ejecutar git pull"; exit 1; }

# Asegurar que el script sea ejecutable después de la actualización
chmod +x deploy.sh

# Buscar el PID del proceso que contiene 'seguridadallimite'
PID=$(pgrep -f 'seguridadallimite')

# Verificar si encontró algún proceso
if [[ -n "$PID" ]]; then
    echo "==== Procesos de Java activos antes de eliminar el proceso ===="
    ps aux | grep java | grep -v grep
    echo
    echo "Matando proceso 'seguridadallimite' con PID: $PID..."
    sudo kill -9 $PID || { echo "Error al matar el proceso"; exit 1; }
    echo
    echo "==== Procesos de Java después de eliminar el proceso ===="
    ps aux | grep java | grep -v grep
    echo "Proceso eliminado. Esperando 3 segundos antes de reiniciar..."
    sleep 3
else
    echo "No se encontró ningún proceso con el nombre 'seguridadallimite'."
fi

# Ejecutar mvn clean package para compilar la aplicación
echo "Ejecutando 'mvn clean package'..."
mvn clean package || { echo "Error durante la compilación con Maven"; exit 1; }

# Navegar al directorio target y verificar si el archivo JAR existe
echo "Navegando al directorio target..."
cd ./target/ || { echo "Error: No se pudo acceder al directorio ./target"; exit 1; }

if [[ ! -f seguridadallimite-1.0.jar ]]; then
    echo "Error: El archivo seguridadallimite-1.0.jar no se encontró en ./target"
    exit 1
fi

# Iniciar la aplicación en segundo plano y redirigir la salida a un archivo log
echo "Iniciando la aplicación..."
nohup java -jar seguridadallimite-1.0.jar > seguridadallimite.log 2>&1 &

# Obtener el nuevo PID para confirmación
NEW_PID=$!
echo "Aplicación reiniciada exitosamente con PID: $NEW_PID"
