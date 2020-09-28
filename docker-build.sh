#!/usr/bin/env bash


if command -v mvn &> /dev/null
then
  mvn -DskipTests package
fi

docker build -t dreamworkerln/ltm-app:latest -f infrastructure/docker/web/Dockerfile .
docker build -t dreamworkerln/ltm-auth:latest -f infrastructure/docker/auth-server/Dockerfile .
docker build -t dreamworkerln/ltm-auth:latest -f infrastructure/docker/auth-server/Dockerfile .
