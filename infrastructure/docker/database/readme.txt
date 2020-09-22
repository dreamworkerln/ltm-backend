1. Создать базу на локальном postgres:

postgres_local_create_database_ltm.sh 




2. Запустить postgres из docker(база создается автоматически при первом запуске):

docker_run_ltm-postgres.sh

(windows 10 в powershell
sh docker_run_ltm-postgres.sh)

При этом файлы базы cms разместятся на локальной машине в ${HOME}/pgdata/ltm/
а порт postgres будет 5432

Проверить: 
psql -h localhost -p 5432 -U ltmadmin ltm
