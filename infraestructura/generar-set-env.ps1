# Genera set_env.bat a partir de .env (Windows / Maven local).
# Uso:  powershell -NoProfile -ExecutionPolicy Bypass -File generar-set-env.ps1
#       (lo invoca start-local.bat en opciones 4 y 5)

$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
$envFile = Join-Path $root '.env'
$outFile = Join-Path $root 'set_env.bat'

if (-not (Test-Path -LiteralPath $envFile)) {
    Write-Host "[ERROR] No existe .env en $root" -ForegroundColor Red
    Write-Host "        Copia .env.example a .env y configura DB_USER, DB_PASSWORD, JWT_SECRET, etc."
    exit 1
}

$sb = [System.Text.StringBuilder]::new()
[void]$sb.AppendLine('@echo off')
[void]$sb.AppendLine('REM === Auto-generado por generar-set-env.ps1 (no editar a mano) ===')
[void]$sb.AppendLine('REM Spring Boot usa DB_USER / DB_PASSWORD (usuario de aplicacion; no root).')

Get-Content -LiteralPath $envFile | ForEach-Object {
    $line = $_.Trim()
    if ($line -match '^\s*#' -or $line -eq '') { return }
    $eq = $line.IndexOf('=')
    if ($eq -lt 1) { return }
    $name = $line.Substring(0, $eq).Trim()
    if ($name -notmatch '^[A-Za-z_][A-Za-z0-9_]*$') { return }
    $val = $line.Substring($eq + 1).Trim()
    if (($val.Length -ge 2) -and (
            ($val.StartsWith('"') -and $val.EndsWith('"')) -or
            ($val.StartsWith("'") -and $val.EndsWith("'")))) {
        $val = $val.Substring(1, $val.Length - 2)
    }
    $val = $val -replace '%', '%%'
    [void]$sb.AppendLine("SET `"$name=$val`"")
}

[System.IO.File]::WriteAllText($outFile, $sb.ToString(), [System.Text.Encoding]::ASCII)
Write-Host "OK: $outFile"
