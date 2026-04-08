<#
.SYNOPSIS
  Lanza el restablecimiento de contraseña root de MySQL en Docker vía WSL (donde suele estar el daemon).

.DESCRIPTION
  La lógica está en reset-mysql-root-docker.sh. Se pide la nueva contraseña de forma oculta en WSL.

.PARAMETER ContainerName
  Ej. sst-mysql
#>
param(
    [string] $ContainerName = "sst-mysql"
)

$ErrorActionPreference = "Stop"
$here = Split-Path -Parent $MyInvocation.MyCommand.Path
$sh = Join-Path $here "reset-mysql-root-docker.sh"

if (-not (Test-Path -LiteralPath $sh)) {
    Write-Error "No existe $sh"
}

function Convert-WindowsPathToWsl {
    param([Parameter(Mandatory)][string] $WindowsPath)
    $full = [System.IO.Path]::GetFullPath($WindowsPath)
    if ($full -notmatch '^([A-Za-z]):\\(.*)$') {
        Write-Error "Ruta no es unidad local (C:\...): $WindowsPath"
    }
    $drive = $Matches[1].ToLowerInvariant()
    $suffix = ($Matches[2] -replace '\\', '/')
    return "/mnt/$drive/$suffix"
}

$unixSh = Convert-WindowsPathToWsl $sh

Write-Host "Se ejecutará el script en WSL. Escribe la nueva contraseña de root cuando la pida (no se muestra)." -ForegroundColor Cyan
wsl -- bash "$unixSh" "$ContainerName"
exit $LASTEXITCODE
