#!/bin/bash

set -a

#source ztests/scripts/99-java_home
source ztests/scripts/0-config_params.sh

{
# unit/integration tests
echo -e "---===== UNIT TESTING =====---" && \
ztests/scripts/1-unit-tests.sh && \

# system tests
echo -e "---===== SYSTEM TESTING =====---" && \

echo 'run postgres container' && \
./ztests/scripts/docker_run_ltm-test-postgres.sh && \
ztests/scripts/2-system-tests.sh && \
sl -e;
}
echo 'kill postgres container'
docker container rm -f ltm-tests-postgres



