#!/bin/bash

# allow use bash params as env to pass them to docker-composer to further substitution in docker-compose.yml
set -a
#set -o allexport

source ./config
docker-compose -p ${PROJECT_NAME} up -d
#docker-compose -p ${PROJECT_NAME} up
