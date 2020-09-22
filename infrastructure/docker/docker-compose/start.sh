#!/bin/bash
set -o allexport
source config

echo ${POSTGRES_URL}

docker-compose -p ${PROJECT_NAME} up
