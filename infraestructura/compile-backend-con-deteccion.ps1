# Compila el backend con Maven y, en paralelo, muestrea GET /cursosback/auth/public-access
# para inferir si hubo reinicio (caida breve + vuelta a OK), tipico de spring-boot-devtools.
param(
    [Parameter(Mandatory = $false)]
    [string] $BackRoot = (Join-Path $PSScriptRoot '..\back')
)

$ErrorActionPreference = 'Continue'
try {
    $BackRoot = (Resolve-Path -LiteralPath $BackRoot).Path
} catch {
    Write-Host "[ERROR] Carpeta backend invalida: $BackRoot"
    exit 1
}
$mvnCmd = Join-Path $BackRoot 'mvn-java21.cmd'
if (-not (Test-Path -LiteralPath $mvnCmd)) {
    Write-Host "[ERROR] No se encontro mvn-java21.cmd en: $BackRoot"
    exit 1
}

$uri = 'http://localhost:8090/cursosback/auth/public-access'

function Test-BackendOnce {
    try {
        Invoke-WebRequest -Uri $uri -UseBasicParsing -TimeoutSec 2 | Out-Null
        return $true
    } catch {
        return $false
    }
}

$wasUp = Test-BackendOnce
$stopFile = Join-Path $env:TEMP ("seg-back-compile-stop-{0}.txt" -f [guid]::NewGuid().ToString('n'))
Remove-Item -LiteralPath $stopFile -Force -ErrorAction SilentlyContinue

$job = Start-Job -Name SegBackWatch -ArgumentList $uri, $stopFile -ScriptBlock {
    param($u, $stopF)
    $sb = [System.Text.StringBuilder]::new(4096)
    while ($true) {
        try {
            $null = Invoke-WebRequest -Uri $u -UseBasicParsing -TimeoutSec 2
            [void]$sb.Append('U')
        } catch {
            [void]$sb.Append('D')
        }
        Start-Sleep -Milliseconds 280
        if (Test-Path -LiteralPath $stopF) { break }
    }
    return $sb.ToString()
}

try {
    Push-Location $BackRoot
    & $mvnCmd @('compile', '-Dmaven.test.skip=true')
    $mvnCode = $LASTEXITCODE
} finally {
    Pop-Location
}

New-Item -Path $stopFile -ItemType File -Force | Out-Null
$null = Wait-Job -Job $job -Timeout 30
$trace = ''
$jobState = $job.State
try {
    $out = Receive-Job -Job $job -ErrorAction SilentlyContinue
    if ($null -ne $out) {
        if ($out -is [string]) {
            $trace = $out
        } elseif ($out.Count -gt 0) {
            $trace = [string]$out[-1]
        }
    }
} finally {
    Stop-Job -Job $job -Force -ErrorAction SilentlyContinue
    Remove-Job -Job $job -Force -ErrorAction SilentlyContinue
    Remove-Item -LiteralPath $stopFile -Force -ErrorAction SilentlyContinue
}

if ($mvnCode -ne 0) {
    Write-Host '[ERROR] mvn compile fallo.'
    exit $mvnCode
}

if ([string]::IsNullOrEmpty($trace)) {
    if ($jobState -eq 'Failed') {
        Write-Host '[AVISO] El monitoreo de red fallo; compilacion OK. No se pudo deducir si hubo reinicio.'
    } else {
        Write-Host '[AVISO] Sin traza de muestreo; compilacion OK. No se pudo deducir si hubo reinicio.'
    }
    exit 0
}

$firstD = $trace.IndexOf('D')
$hadDown = $firstD -ge 0
$hadUpAfterFirstDown = $hadDown -and ($trace.LastIndexOf('U') -gt $firstD)

Write-Host ''
Write-Host '--- Resultado (muestreo HTTP durante compile) ---'
if (-not $wasUp) {
    Write-Host '[AVISO] Antes de compilar el API no respondia en :8090 (no se puede deducir reinicio).'
    Write-Host '        Arranca el backend con opcion 5 o 4 y vuelve a usar la opcion 8.'
} elseif (-not $hadDown) {
    Write-Host '[INFO] Sin caida detectable: el API respondio en todo el muestreo (no se vio reinicio por red).'
    Write-Host '        Si los cambios no aplican, usa opcion 9 (reinicio forzado).'
} elseif ($hadUpAfterFirstDown) {
    Write-Host '[OK] Reinicio detectado: caida breve del API y recuperacion (coherente con DevTools).'
} elseif ($trace.LastIndexOf('U') -lt 0) {
    Write-Host '[ERROR] El API estuvo caido durante el muestreo y no hubo respuestas OK; revisa la consola del backend.'
} else {
    Write-Host '[AVISO] Hubo caida pero al final el patron no confirma recuperacion clara; revisa consola o usa opcion 9.'
}

exit 0
