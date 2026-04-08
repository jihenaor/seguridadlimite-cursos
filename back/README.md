mysql -u tu_usuario -p

USE wwsegu_cursos;

ALTER TABLE sl_disenocurricular
CHANGE COLUMN unidad unidad VARCHAR(40) NOT NULL;

DESCRIBE sl_disenocurricular;

sudo kill -9 `sudo lsof -t -i:9001`

ssh-keygen -R 31.220.57.203

ssh -o ServerAliveInterval=60 -o ServerAliveCountMax=30 -L 3306:localhost:3306 root@31.220.57.203
