@echo off
setlocal EnableExtensions

REM Wrapper: ejecuta mvnw con JDK 21 aunque en PATH haya otro Java (p. ej. 17).
REM
REM Uso habitual:
REM   mvn-java21.cmd spring-boot:run
REM
REM Maven, antes de arrancar, ejecuta fases previas del plugin (enforcer, resources,
REM compile de ~485 fuentes, testCompile de tests, etc.): es NORMAL y la primera vez
REM o tras "clean" es lenta; en cambios pequeños suele recompilar solo lo tocado.
REM
REM Opcional — algo más rápido en dev (no compila tests):
REM   mvn-java21.cmd spring-boot:run -Dmaven.test.skip=true
REM
REM -DskipTests solo evita EJECUTAR tests; igual compila test-sources (menos ahorro).
REM
REM Otros:
REM   mvn-java21.cmd clean compile -DskipTests

set "_BACK=%~dp0"

REM Si JAVA_HOME ya apunta a un JDK 21, usarlo
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\javac.exe" (
  for /f "tokens=2" %%j in ('"%JAVA_HOME%\bin\javac.exe" -version 2^>^&1') do (
    echo %%j | findstr /r "^21\." >nul && goto :jdk_ok
  )
)

REM Buscar instalaciones típicas de JDK 21 en Windows
for /d %%D in ("C:\Program Files\Eclipse Adoptium\jdk-21*") do (
  if exist "%%~D\bin\javac.exe" (
    set "JAVA_HOME=%%~D"
    goto :jdk_ok
  )
)
for /d %%D in ("C:\Program Files\Microsoft\jdk-21*") do (
  if exist "%%~D\bin\javac.exe" (
    set "JAVA_HOME=%%~D"
    goto :jdk_ok
  )
)
for /d %%D in ("C:\Program Files\Java\jdk-21*") do (
  if exist "%%~D\bin\javac.exe" (
    set "JAVA_HOME=%%~D"
    goto :jdk_ok
  )
)

echo.
echo [ERROR] No se encontro JDK 21. El proyecto compila con --release 21.
echo   - Instala Temurin 21: https://adoptium.net/
echo   - O define JAVA_HOME al JDK 21 ^(carpeta que contiene bin\javac.exe^)
echo   - Verifica:  "%JAVA_HOME%\bin\javac.exe" -version   ^(debe mostrar 21.x^)
echo.
exit /b 1

:jdk_ok
set "PATH=%JAVA_HOME%\bin;%PATH%"
echo [INFO] JDK 21: %JAVA_HOME%
pushd "%_BACK%"
call mvnw.cmd %*
set "ERR=%ERRORLEVEL%"
popd
exit /b %ERR%
