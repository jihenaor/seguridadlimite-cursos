# Cierra procesos que escuchan en TCP 8090 (backend Spring Boot local habitual).
# Llamado desde start-local.bat (opcion 9). Requiere PowerShell 5+ / Get-NetTCPConnection.
$ErrorActionPreference = 'SilentlyContinue'
$listenerPids = Get-NetTCPConnection -LocalPort 8090 -State Listen |
    Select-Object -ExpandProperty OwningProcess -Unique

if (-not $listenerPids) {
    Write-Host '  (Nadie escuchaba en 8090)'
    exit 0
}

foreach ($procId in @($listenerPids)) {
    Write-Host ("  Stop-Process PID " + $procId)
    Stop-Process -Id $procId -Force -ErrorAction SilentlyContinue
}
exit 0
