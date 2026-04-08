<#

.SYNOPSIS

  Alinea en MySQL (Docker) usuario/contraseña del .env con la base real (corrige 1045 tras cambiar .env).



.DESCRIPTION

  MYSQL_USER / MYSQL_PASSWORD del compose solo se aplican al crear el volumen vacío.

  Si cambiaste DB_PASSWORD después, hace falta ALTER USER como root.



  Si Docker solo está en WSL (sin Docker Desktop en Windows), este script usa automáticamente

  "wsl docker" y convierte rutas para docker cp.



  MYSQL_ROOT_PASSWORD se toma de .env y se inyecta con docker exec -e (no depende del env del contenedor).

  Antes de cambiar nada, valida claves: .\scripts\validar-contrasenas-mysql-docker.ps1 -ContainerName <nombre>



.PARAMETER ContainerName

  Por defecto mysql8 (compose de back/). Ej. WSL: sst-mysql



.PARAMETER RootPassword

  Contraseña real de root MySQL (si no coincide con MYSQL_ROOT_PASSWORD del .env). Ej. tras reset-mysql-root-docker.sh sin actualizar .env.

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



# Docker en Windows (Desktop) vs daemon solo en WSL

# Con ErrorAction Stop, "docker info" sin Desktop escribe en stderr y PowerShell aborta antes de probar wsl.

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

No hay Docker usable desde Windows.

- Arranca Docker Desktop, o

- Instala/usa Docker en WSL y prueba: wsl docker info

- O ejecuta desde WSL: bash scripts/actualizar-usuario-mysql-docker.sh $ContainerName

"@

    }

    $script:DockerViaWsl = $true

    Write-Host "Usando wsl docker (el daemon está en WSL)." -ForegroundColor DarkYellow

}



function Test-ContainerRunning {

    param([string] $Name)

    if ($script:DockerViaWsl) {

        $raw = wsl docker ps --format "{{.Names}}"

    } else {

        $raw = docker ps --format "{{.Names}}"

    }

    if ([string]::IsNullOrWhiteSpace($raw)) { return $false }

    $names = @($raw -split "`r?`n" | ForEach-Object { $_.Trim() } | Where-Object { $_ })

    return $names -contains $Name

}

# wslpath desde PowerShell suele romperse sin comillas (se pierden \). Evitamos wslpath.
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

        Write-Error "Falta MYSQL_ROOT_PASSWORD en .env, o usa -RootPassword 'clave_root_real'"

    }

    $rootPass = $map["MYSQL_ROOT_PASSWORD"]

} else {

    $rootPass = $RootPassword

}



if (-not (Test-ContainerRunning -Name $ContainerName)) {

    Write-Error "No hay contenedor en ejecución llamado '$ContainerName'. En WSL: docker ps"

}



$dbUser = $map["DB_USER"]

$dbName = $map["DB_NAME"]

$dbPass = $map["DB_PASSWORD"]



if ($dbName -notmatch '^[a-zA-Z0-9_]+$') {

    Write-Error "DB_NAME debe ser identificador simple (letras, números, _): $dbName"

}



# Comilla simple SQL: ' -> ''

$u = $dbUser -replace "'", "''"

$p = $dbPass -replace "'", "''"

$d = $dbName -replace "'", "''"



$sql = @"

CREATE USER IF NOT EXISTS '$u'@'%' IDENTIFIED WITH mysql_native_password BY '$p';

ALTER USER '$u'@'%' IDENTIFIED WITH mysql_native_password BY '$p';

GRANT ALL PRIVILEGES ON ``${d}``.* TO '$u'@'%';

FLUSH PRIVILEGES;

"@



$tmp = [System.IO.Path]::GetTempFileName()

$envPwdFile = [System.IO.Path]::GetTempFileName()

try {

    [System.IO.File]::WriteAllText($tmp, $sql, [System.Text.UTF8Encoding]::new($false))

    # --env-file evita que wsl/docker partan la contraseña en -e (%, =, comillas, etc.)
    $envContent = "MYSQL_PWD=$rootPass`n"
    [System.IO.File]::WriteAllText($envPwdFile, $envContent, [System.Text.UTF8Encoding]::new($false))



    if ($script:DockerViaWsl) {

        $wslPath = Convert-WindowsPathToWsl -WindowsPath $tmp

        $wslEnvPath = Convert-WindowsPathToWsl -WindowsPath $envPwdFile

        wsl -- docker cp "$wslPath" "${ContainerName}:/tmp/fix-app-user.sql" | Out-Null

        wsl -- docker exec --env-file $wslEnvPath $ContainerName mysql -uroot -e "source /tmp/fix-app-user.sql"

    } else {

        docker cp $tmp "${ContainerName}:/tmp/fix-app-user.sql" | Out-Null

        docker exec --env-file $envPwdFile $ContainerName mysql -uroot -e "source /tmp/fix-app-user.sql"

    }



    if ($LASTEXITCODE -ne 0) {

        Write-Error @"
mysql falló con root (1045). La clave en MYSQL_ROOT_PASSWORD del .env no coincide con la de MySQL.

  - Si usaste reset-mysql-root-docker.sh: copia esa contraseña a back/.env en MYSQL_ROOT_PASSWORD, o ejecuta:
    pwsh -File .\scripts\actualizar-usuario-mysql-docker.ps1 -ContainerName $ContainerName -RootPassword 'LA_CLAVE_QUE_PUSISTE_AL_RESET'

  - Prueba en WSL: docker exec -e MYSQL_PWD='tu_root' $ContainerName mysql -uroot -e "SELECT 1"
"@

    }

}

finally {

    if (Test-Path -LiteralPath $tmp) { Remove-Item -LiteralPath $tmp -Force -ErrorAction SilentlyContinue }

    if (Test-Path -LiteralPath $envPwdFile) { Remove-Item -LiteralPath $envPwdFile -Force -ErrorAction SilentlyContinue }

}



Write-Host "Usuario '$dbUser' actualizado. Reinicia la API Spring." -ForegroundColor Green

