#!/usr/bin/env bash


#sudo -u postgres PGOPTIONS=--search_path=ltm psql --dbname=ltm -f infrastructure/database/purge_schema.sql

#echo "localhost:5442:ltm:ltmadmin:ltmadminpassword" > ~/.pgpass
#echo "localhost:5432:ltm:ltmadmin:ltmadminpassword" >> ~/.pgpass
#chmod go-rwx ~/.pgpass

echo "port 5442:"
PGOPTIONS=--search_path=ltm psql postgresql://ltmadmin:ltmadminpassword@localhost:5442/ltm -f infrastructure/docker/database/purge_schema.sql
echo "port 5432:"
PGOPTIONS=--search_path=ltm psql postgresql://ltmadmin:ltmadminpassword@localhost:5432/ltm -f infrastructure/docker/database/purge_schema.sql

#-h localhost -p 5442 -U ltmadmin --dbname=ltm