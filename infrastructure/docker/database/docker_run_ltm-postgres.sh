#!/usr/bin/env bash
PGDATA_LTM=${HOME}/pgdata/ltm
mkdir -p $PGDATA_LTM

docker run -d --rm \
--name ltm-postgres \
-e POSTGRES_PASSWORD=gjhUY876787ytuh87gdf \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v $PGDATA_LTM:/var/lib/postgresql/data \
-p 127.0.0.1:5432:5432 \
dreamworkerln/ltm-postgres
