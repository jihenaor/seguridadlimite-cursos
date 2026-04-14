@echo off
REM =============================================================
REM  start-local.bat — Stack de desarrollo local (Windows)
REM  Ejecutar desde esta carpeta: C:\...\seguridad\infraestructura
REM
REM  Estructura del monorepo:
REM    ..\front\           Angular App
REM    ..\back\            Spring Boot API
REM    ..\infraestructura\ Docker, Nginx, este script
REM
REM  Opciones 4 y 5: generan set_env.bat y llaman ..\back\mvn-java21.cmd
REM    La primera compilacion puede tardar varios minutos.
REM  Opcion 8: compile-backend-con-deteccion.ps1 (muestreo HTTP en :8090 durante mvn compile).
REM  Opcion 9: matar-puerto-8090.ps1 + Spring Boot de nuevo (sin Docker; evita ^ en -Command).
REM =============================================================
setlocal

IF NOT EXIST ".env" (
    echo.
    echo [ERROR] No se encontro el archivo .env
    echo   Copia .env.example a .env y completa los valores.
    echo.
    pause
    exit /b 1
)

:MENU
echo.
echo  ===================================================
echo   Seguridad al Limite - Entorno de Desarrollo
echo  ===================================================
echo.
echo   CON DOCKER
echo     1. Stack completo en Docker (back + front + db)
echo     2. Hibrido: backend Docker, front con npm
echo.
echo   SIN DOCKER
echo     3. Solo front con npm  (backend ya corriendo)
echo     4. Backend Maven + front npm  (MySQL local)
echo     5. Solo backend con Maven     (MySQL local)
echo.
echo   MANTENIMIENTO BACKEND (sin Docker, puerto 8090)
echo     8. Recompilar Maven ^(informe automatico: reinicio si / no^)
echo     9. Reiniciar backend (cierra :8090 y abre Spring Boot otra vez)
echo.
echo   UTILIDADES DOCKER
echo     6. Detener contenedores Docker
echo     7. Ver logs Docker
echo     0. Salir
echo.
echo  ===================================================
echo.
set /p OPCION="Elige una opcion (0-9): "

IF "%OPCION%"=="1" goto DOCKER_FULL
IF "%OPCION%"=="2" goto DOCKER_HIBRIDO
IF "%OPCION%"=="3" goto LOCAL_FRONT
IF "%OPCION%"=="4" goto LOCAL_FULL
IF "%OPCION%"=="5" goto LOCAL_BACK
IF "%OPCION%"=="6" goto DOCKER_STOP
IF "%OPCION%"=="7" goto DOCKER_LOGS
IF "%OPCION%"=="8" goto LOCAL_BACK_COMPILE
IF "%OPCION%"=="9" goto LOCAL_BACK_RESTART
IF "%OPCION%"=="0" exit /b 0

echo Opcion invalida.
goto MENU


REM =============================================================

:DOCKER_FULL
echo.
echo [Paso 1/1] Verificando Docker...
docker info >nul 2>&1
if errorlevel 1 ( echo [ERROR] Docker no esta corriendo. & echo. & pause & goto MENU )
echo.
echo Levantando stack completo (build desde codigo local)...
docker compose -f docker-compose.yml -f docker-compose.local.yml --env-file .env up --build -d
if errorlevel 1 ( echo [ERROR] Fallo. Revisa: docker compose logs --tail=50 & pause & goto MENU )
echo.
echo   Frontend  -> http://localhost:4200
echo   API       -> http://localhost:8090/cursosback
echo   Gateway   -> http://localhost
echo.
pause
goto MENU


:DOCKER_HIBRIDO
echo.
echo [Paso 1/3] Verificando Docker...
docker info >nul 2>&1
if errorlevel 1 ( echo [ERROR] Docker no esta corriendo. & echo. & pause & goto MENU )
echo [Paso 2/3] Verificando Node.js...
node --version >nul 2>&1
if errorlevel 1 ( echo [ERROR] Node.js no encontrado. https://nodejs.org & echo. & pause & goto MENU )
echo [Paso 3/3] Levantando mysql, phpMyAdmin y backend en Docker...
docker compose -f docker-compose.yml -f docker-compose.local.yml --env-file .env up -d mysql phpmyadmin backend
echo.
call :INSTALL_FRONT
if errorlevel 1 goto MENU
echo.
start "Frontend [:4200]" cmd /k "cd /d ..\front && npm start"
echo Frontend -> :4200   phpMyAdmin -> http://localhost:8180
echo Backend en Docker -> http://localhost:8090/cursosback
echo Usa opcion 6 para detener Docker.
echo.
pause
goto MENU


:LOCAL_FRONT
echo.
echo [Paso 1/2] Verificando Node.js...
node --version >nul 2>&1
if errorlevel 1 ( echo [ERROR] Node.js no encontrado. https://nodejs.org & echo. & pause & goto MENU )
echo [Paso 2/2] Verificando dependencias npm...
call :INSTALL_FRONT
if errorlevel 1 goto MENU
echo.
echo [AVISO] El backend debe estar corriendo en http://localhost:8090
echo.
start "Frontend [:4200]" cmd /k "cd /d ..\front && npm start"
echo Frontend -> :4200
echo.
pause
goto MENU


:LOCAL_FULL
echo.
echo [Paso 1/4] Verificando Node.js...
node --version >nul 2>&1
if errorlevel 1 ( echo [ERROR] Node.js no encontrado. https://nodejs.org & echo. & pause & goto MENU )
echo [Paso 2/4] Verificando Java...
java -version >nul 2>&1
if errorlevel 1 ( echo [ERROR] Java no encontrado. https://adoptium.net ^(JDK 21^) & echo. & pause & goto MENU )
echo [Paso 3/4] Verificando MySQL en localhost:3306...
powershell -Command "if (Test-NetConnection localhost -Port 3306 -InformationLevel Quiet) { exit 0 } else { exit 1 }" >nul 2>&1
if errorlevel 1 (
    echo [AVISO] MySQL no detectado en localhost:3306.
    set /p CONT="Continuar de todas formas? (S/N): "
    if /i NOT "%CONT%"=="S" goto MENU
)
echo [Paso 4/4] Verificando dependencias npm...
call :INSTALL_FRONT
if errorlevel 1 goto MENU
echo.
echo Generando set_env.bat desde .env...
call :CREAR_SET_ENV
if errorlevel 1 ( echo [ERROR] No se pudo generar set_env.bat. Revisa .env & pause & goto MENU )
echo.
echo Abriendo backend con Maven (JDK 21)...
start "Backend Spring Boot [:8090]" cmd /k "cd /d %~dp0 && call set_env.bat && cd /d ..\back && mvn-java21.cmd spring-boot:run -Dmaven.test.skip=true"
echo Tras cambios Java: 8=compile ^(dice si hubo reinicio^)  9=forzado :8090.
echo Esperando que Spring Boot inicie (15 seg)...
timeout /t 15 /nobreak >nul
echo.
start "Frontend [:4200]" cmd /k "cd /d ..\front && npm start"
echo.
echo Backend :8090   Frontend :4200
echo Cierra cada terminal para detener el servicio.
echo Desde este menu: 8=recompilar ^(informe automatico^)  9=forzado :8090
echo.
pause
goto MENU


:LOCAL_BACK
echo.
echo [Paso 1/2] Verificando Java...
java -version >nul 2>&1
if errorlevel 1 ( echo [ERROR] Java no encontrado. https://adoptium.net ^(JDK 21^) & echo. & pause & goto MENU )
echo [Paso 2/2] Verificando MySQL en localhost:3306...
powershell -Command "if (Test-NetConnection localhost -Port 3306 -InformationLevel Quiet) { exit 0 } else { exit 1 }" >nul 2>&1
if errorlevel 1 (
    echo [AVISO] MySQL no detectado en localhost:3306.
    set /p CONT="Continuar de todas formas? (S/N): "
    if /i NOT "%CONT%"=="S" goto MENU
)
echo.
echo Generando set_env.bat desde .env...
call :CREAR_SET_ENV
if errorlevel 1 ( echo [ERROR] No se pudo generar set_env.bat. Revisa .env & pause & goto MENU )
echo.
echo Abriendo backend con Maven (JDK 21)...
start "Backend Spring Boot [:8090]" cmd /k "cd /d %~dp0 && call set_env.bat && cd /d ..\back && mvn-java21.cmd spring-boot:run -Dmaven.test.skip=true"
echo API: http://localhost:8090/cursosback
echo Vuelve a este menu: 8=recompilar ^(informe reinicio^)  9=forzado :8090
echo.
pause
goto MENU


:LOCAL_BACK_COMPILE
echo.
echo [Paso 1/2] Verificando Java...
java -version >nul 2>&1
if errorlevel 1 ( echo [ERROR] Java no encontrado. https://adoptium.net ^(JDK 21^) & echo. & pause & goto MENU )
echo [Paso 2/2] Maven compile + muestreo HTTP ^(8090/cursosback/auth/public-access^)...
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0compile-backend-con-deteccion.ps1" -BackRoot "%~dp0..\back"
if errorlevel 1 ( echo. & pause & goto MENU )
echo.
echo Cierre automatico en 8 segundos ^(o pulsa una tecla^)...
timeout /t 8
goto MENU


:LOCAL_BACK_RESTART
echo.
echo [AVISO] Se cerrara el proceso que escucha en TCP 8090 ^(backend local habitual^).
echo   Asegurate de no tener otra aplicacion importante en ese puerto.
set /p CONT="Continuar? (S/N): "
if /i NOT "%CONT%"=="S" goto MENU
echo.
echo [Paso 1/5] Verificando Java...
java -version >nul 2>&1
if errorlevel 1 ( echo [ERROR] Java no encontrado. https://adoptium.net ^(JDK 21^) & echo. & pause & goto MENU )
echo [Paso 2/5] Verificando MySQL en localhost:3306...
powershell -Command "if (Test-NetConnection localhost -Port 3306 -InformationLevel Quiet) { exit 0 } else { exit 1 }" >nul 2>&1
if errorlevel 1 (
    echo [AVISO] MySQL no detectado en localhost:3306.
    set /p CONTDB="Continuar de todas formas? (S/N): "
    if /i NOT "%CONTDB%"=="S" goto MENU
)
echo [Paso 3/5] Cerrando listener en puerto 8090...
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0matar-puerto-8090.ps1"
echo [Paso 4/5] Esperando liberacion del puerto...
timeout /t 2 /nobreak >nul
echo [Paso 5/5] Generando set_env.bat y arrancando Spring Boot...
call :CREAR_SET_ENV
if errorlevel 1 ( echo [ERROR] No se pudo generar set_env.bat. Revisa .env & pause & goto MENU )
echo.
start "Backend Spring Boot [:8090]" cmd /k "cd /d %~dp0 && call set_env.bat && cd /d ..\back && mvn-java21.cmd spring-boot:run -Dmaven.test.skip=true"
echo API: http://localhost:8090/cursosback
echo Cierra la ventana del backend para detenerlo.
echo.
pause
goto MENU


:DOCKER_STOP
echo.
docker compose -f docker-compose.yml -f docker-compose.local.yml --env-file .env down
echo Contenedores detenidos.
echo.
pause
goto MENU

:DOCKER_LOGS
echo.
docker compose -f docker-compose.yml -f docker-compose.local.yml --env-file .env logs -f --tail=100
pause
goto MENU


REM =============================================================
REM  SUBRUTINAS
REM =============================================================

:INSTALL_FRONT
echo Verificando dependencias del frontend...
if not exist "..\front\node_modules" (
    echo   Instalando... puede tardar varios minutos
    pushd ..\front
    call npm install --legacy-peer-deps
    if errorlevel 1 (
        echo [ERROR] npm install fallo en front.
        popd
        pause
        exit /b 1
    )
    popd
) else (
    echo   front OK
)
exit /b 0

:CREAR_SET_ENV
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0generar-set-env.ps1"
exit /b %ERRORLEVEL%
