@echo off
setlocal
rem Uso (desde cualquier sitio):
rem   scripts\actualizar-usuario-mysql-docker.cmd
rem   scripts\actualizar-usuario-mysql-docker.cmd sst-mysql
rem No hace falta chmod: se invoca con bash dentro de WSL.

set "CONTAINER=%~1"
if "%CONTAINER%"=="" set "CONTAINER=mysql8"

set "BACK_ROOT=%~dp0.."
pushd "%BACK_ROOT%" >nul
set "WIN_BACK=%CD%"

for /f "usebackq delims=" %%i in (`wsl wslpath "%WIN_BACK%" 2^>nul`) do set "UBACK=%%i"
if "%UBACK%"=="" (
  echo [ERROR] No se pudo usar WSL/wslpath. Opciones:
  echo   1^) PowerShell: pwsh -File scripts\actualizar-usuario-mysql-docker.ps1 -ContainerName sst-mysql
  echo   2^) WSL a mano: wsl -e bash -lc "cd ruta/al/back ^&^& bash scripts/actualizar-usuario-mysql-docker.sh sst-mysql"
  popd >nul
  exit /b 1
)

wsl -e bash -lc "cd \"%UBACK%\" && bash scripts/actualizar-usuario-mysql-docker.sh %CONTAINER%"
set ERR=%ERRORLEVEL%
popd >nul
exit /b %ERR%
