#!/usr/bin/env bash
#PGDATA_LTM=${HOME}/pgdata/ltm
#mkdir -p $PGDATA_LTM

docker run -d --rm \
--name ltm-tests-postgres \
-e POSTGRES_PASSWORD=gjhUY876787ytuh87gdf \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-p 127.0.0.1:5442:5432 \
dreamworkerln/ltm-postgres
#-v $PGDATA_LTM:/var/lib/postgresql/data \
