# ltm-backend

#### INSTALL && RUN

Install docker & docker-compose
```
https://docs.docker.com/get-docker/
https://docs.docker.com/compose/install/
```

then

```
cd ltm-backend/infrastructure/docker-compose/  
``` 

to start  
```
start.sh
```

to stop  
```
stop.sh
```
 
##### connect to postgresql
PGOPTIONS=--search_path=ltm PAGER="less -S -iMSx4 -RSFX -e" psql -h localhost -p 5432 -U ltmadmin ltm
