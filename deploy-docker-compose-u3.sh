#!/bin/bash

#BUILD PROJECTS
mvn -DskipTests package

#CREATE DIRECTORIES
ssh u3-root "cd /root/projects/ltm-backend/ && mkdir -p ltmapp auth-server"

#SENDING CONFIGS & JARS
rsync -av --chown  root:root --delete infrastructure u3-root:/root/projects/ltm-backend/
rsync -avz --chown root:root --delete ltmapp/target/*.jar u3-root:/root/projects/ltm-backend/ltmapp/target/
rsync -avz --chown root:root --delete auth-server/target/*.jar u3-root:/root/projects/ltm-backend/auth-server/target/
rsync -av --chown  root:root --delete docker-build.sh purge_schema.sh u3-root:/root/projects/ltm-backend/

# BUILD DOCKER IMAGES ON REMOTE
ssh u3-root "cd /root/projects/ltm-backend/ && ./docker-build.sh"


