# ltm-backend

#### INSTALL && RUN

Install docker & docker-compose
```
https://docs.docker.com/get-docker/
https://docs.docker.com/compose/install/
```

cd ltm-backend/infrastructure/docker/docker-compose/  

to start use  
```
start.sh
```

To stop  
```
stop.sh
```
 
##### connect to postgresql
PGOPTIONS=--search_path=ltm PAGER="less -S -iMSx4 -RSFX -e" psql -h localhost -p 5432 -U ltmadmin ltm
