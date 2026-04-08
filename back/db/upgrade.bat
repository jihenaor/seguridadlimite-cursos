@echo off
REM Usuario de aplicación (no root). Define DB_USER y DB_PASSWORD antes de ejecutar.
if "%DB_USER%"=="" set DB_USER=seguridadlimite_app
if "%DB_PASSWORD%"=="" (
  echo Defina DB_PASSWORD ^(misma que en back/.env o docker-compose^^).
  exit /b 1
)
liquibase --driver=com.mysql.jdbc.Driver --classpath="C:\Users\Usuario\Documents\mysql\mysql-connector-java-5.1.49-bin.jar" --changeLogFile=db-changelog.xml --url="jdbc:mysql://localhost:3306/wwsegu_cursos?zeroDateTimeBehavior=convertToNull" --username=%DB_USER% --password=%DB_PASSWORD% update
