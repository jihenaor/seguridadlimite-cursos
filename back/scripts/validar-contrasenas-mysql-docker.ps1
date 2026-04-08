<#

.SYNOPSIS

  Comprueba que MYSQL_ROOT_PASSWORD y DB_PASSWORD del .env permiten conectar a MySQL en Docker (sin cambiar nada).


.DESCRIPTION

  Ejecuta SELECT 1 como root y como usuario de aplicación sobre DB_NAME. Útil antes de actualizar-usuario-mysql-docker.ps1.

  Misma detección Docker Desktop / wsl docker que el script de actualización.


.PARAMETER ContainerName

  Ej. sst-mysql o mysql8


.PARAMETER RootPassword

  Si la clave root real no coincide con MYSQL_ROOT_PASSWORD en .env.

#>

param(

    [string] $ContainerName = "mysql8",

    [string] $RootPassword = ""

)



$ErrorActionPreference = "Stop"



$here = Split-Path -Parent $MyInvocation.MyCommand.Path

$backRoot = Split-Path -Parent $here

$envPath = Join-Path $backRoot ".env"



if (-not (Test-Path -LiteralPath $envPath)) {

    Write-Error "No existe $envPath"

}



# Docker Desktop vs wsl docker (misma lógica que actualizar-usuario-mysql-docker.ps1)

$script:DockerViaWsl = $false

$prevEa = $ErrorActionPreference

$dockerWinExit = -1

try {

    $ErrorActionPreference = "SilentlyContinue"

    docker info 2>&1 | Out-Null

    $dockerWinExit = $LASTEXITCODE

} finally {

    $ErrorActionPreference = $prevEa

}



if ($dockerWinExit -ne 0) {

    $dockerWslExit = -1

    try {

        $ErrorActionPreference = "SilentlyContinue"

        wsl docker info 2>&1 | Out-Null

        $dockerWslExit = $LASTEXITCODE

    } finally {

        $ErrorActionPreference = $prevEa

    }

    if ($dockerWslExit -ne 0) {

        Write-Error @"

No hay Docker usable. Arranca Docker Desktop o usa Docker en WSL (wsl docker info).

"@

    }

    $script:DockerViaWsl = $true

    Write-Host "Usando wsl docker." -ForegroundColor DarkYellow

}



function Test-ContainerRunning {

    param([string] $Name)

    $prevEa2 = $ErrorActionPreference

    try {

        $ErrorActionPreference = "SilentlyContinue"

        if ($script:DockerViaWsl) {

            $raw = wsl docker ps --format "{{.Names}}" 2>&1 | Out-String

        } else {

            $raw = docker ps --format "{{.Names}}" 2>&1 | Out-String

        }

    } finally {

        $ErrorActionPreference = $prevEa2

    }

    if ([string]::IsNullOrWhiteSpace($raw)) { return $false }

    $names = @($raw -split "`r?`n" | ForEach-Object { $_.Trim() } | Where-Object { $_ })

    return $names -contains $Name

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



function Unquote-DotenvValue {

    param([string] $Value)

    if ([string]::IsNullOrWhiteSpace($Value)) { return $Value }

    $v = $Value.Trim()

    if ($v.Length -ge 2) {

        $first = $v.Substring(0, 1)

        $last = $v.Substring($v.Length - 1, 1)

        if (($first -eq '"' -and $last -eq '"') -or ($first -eq "'" -and $last -eq "'")) {

            return $v.Substring(1, $v.Length - 2)

        }

    }

    return $v

}



$map = @{}

Get-Content -LiteralPath $envPath -Encoding UTF8 | ForEach-Object {

    $line = $_.Trim()

    if ($line -eq "" -or $line.StartsWith("#")) { return }

    $i = $line.IndexOf("=")

    if ($i -lt 1) { return }

    $k = $line.Substring(0, $i).Trim()

    $v = Unquote-DotenvValue -Value ($line.Substring($i + 1).Trim())

    $map[$k] = $v

}



foreach ($r in @("DB_USER", "DB_NAME", "DB_PASSWORD")) {

    if (-not $map.ContainsKey($r) -or [string]::IsNullOrWhiteSpace($map[$r])) {

        Write-Error "Falta o está vacío en .env: $r"

    }

}



if ([string]::IsNullOrWhiteSpace($RootPassword)) {

    if (-not $map.ContainsKey("MYSQL_ROOT_PASSWORD") -or [string]::IsNullOrWhiteSpace($map["MYSQL_ROOT_PASSWORD"])) {

        Write-Error "Falta MYSQL_ROOT_PASSWORD en .env, o usa -RootPassword 'clave_root'"

    }

    $rootPass = $map["MYSQL_ROOT_PASSWORD"]

} else {

    $rootPass = $RootPassword

}



$dbUser = $map["DB_USER"]

$dbName = $map["DB_NAME"]

$dbPass = $map["DB_PASSWORD"]



if ($dbName -notmatch '^[a-zA-Z0-9_]+$') {

    Write-Error "DB_NAME debe ser identificador simple: $dbName"

}



if (-not (Test-ContainerRunning -Name $ContainerName)) {

    Write-Error "No hay contenedor en ejecución llamado '$ContainerName'. Prueba: wsl docker ps"

}



function Test-MysqlWithEnvPwd {

    param(

        [string] $Password,

        [string[]] $MysqlArgs

    )



    $envPwdFile = [System.IO.Path]::GetTempFileName()

    try {

        $envContent = "MYSQL_PWD=$Password`n"

        [System.IO.File]::WriteAllText($envPwdFile, $envContent, [System.Text.UTF8Encoding]::new($false))



        $prevEa3 = $ErrorActionPreference

        $mysqlExit = 1

        try {

            $ErrorActionPreference = "SilentlyContinue"

            if ($script:DockerViaWsl) {

                $wslEnvPath = Convert-WindowsPathToWsl -WindowsPath $envPwdFile

                $null = wsl -- docker exec --env-file $wslEnvPath $ContainerName mysql @MysqlArgs 2>&1

            } else {

                $null = docker exec --env-file $envPwdFile $ContainerName mysql @MysqlArgs 2>&1

            }

            $mysqlExit = $LASTEXITCODE

        } finally {

            $ErrorActionPreference = $prevEa3

        }

        return [int] $mysqlExit

    }

    finally {

        if (Test-Path -LiteralPath $envPwdFile) {

            Remove-Item -LiteralPath $envPwdFile -Force -ErrorAction SilentlyContinue

        }

    }

}



Write-Host "Contenedor: $ContainerName" -ForegroundColor Cyan

Write-Host "Comprobando root (MYSQL_PWD desde .env o -RootPassword)..." -ForegroundColor Gray



$rootExit = Test-MysqlWithEnvPwd -Password $rootPass -MysqlArgs @("-uroot", "-e", "SELECT 1 AS root_ok")



$rootCode = [int] $rootExit

if ($rootCode -eq 0) {

    Write-Host "  Root MySQL: OK" -ForegroundColor Green

} else {

    Write-Host "  Root MySQL: FALLA (clave incorrecta o MySQL no acepta root). Código salida: $rootCode" -ForegroundColor Red

}



Write-Host "Comprobando usuario de aplicación ($dbUser) y base $dbName (DB_PASSWORD)..." -ForegroundColor Gray



$appExit = Test-MysqlWithEnvPwd -Password $dbPass -MysqlArgs @("-u$dbUser", "-D$dbName", "-e", "SELECT 1 AS app_ok")

$appCode = [int] $appExit

if ($appCode -eq 0) {

    Write-Host "  Usuario app + base: OK" -ForegroundColor Green

} else {

    Write-Host "  Usuario app + base: FALLA (típico 1045: clave distinta en MySQL o sin permisos). Código: $appCode" -ForegroundColor Red

    Write-Host "  Si root está OK, ejecuta: .\scripts\actualizar-usuario-mysql-docker.ps1 -ContainerName $ContainerName" -ForegroundColor DarkYellow

}



if ($rootCode -ne 0 -or $appCode -ne 0) {

    exit 1

}



Write-Host "Todas las comprobaciones pasaron." -ForegroundColor Green

exit 0
