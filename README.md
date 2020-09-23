# ltm-backend

#### DOCKER POSTGRESS

#### INSTALL && RUN

cd ltm-backend/infrastructure/docker/docker-compose/  

to start use  
start.sh  
  
To stop  
stop.sh  
 
##### connect to postgresql
<br>  
PGOPTIONS=--search_path=ltm PAGER="less -S -iMSx4 -RSFX -e" psql -h localhost -p 5432 -U ltmadmin ltm
