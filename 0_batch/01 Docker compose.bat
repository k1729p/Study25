@echo off
set PROJECT=study25
set COMPOSE_FILE=..\docker-config\compose.yaml

docker container rm --force company > nul 2>&1
docker container rm --force resource-server-a > nul 2>&1
docker container rm --force resource-server-b > nul 2>&1
docker container rm --force graphql-client > nul 2>&1
docker image rm --force %PROJECT%-company > nul 2>&1
docker image rm --force %PROJECT%-resource-server-a > nul 2>&1
docker image rm --force %PROJECT%-resource-server-b > nul 2>&1
docker image rm --force %PROJECT%-graphql-client > nul 2>&1
echo ------------------------------------------------------------------------------------------
docker compose down
docker compose -f %COMPOSE_FILE% -p %PROJECT% up --detach
echo ------------------------------------------------------------------------------------------
docker compose -f %COMPOSE_FILE% -p %PROJECT% ps
echo ------------------------------------------------------------------------------------------
docker compose -f %COMPOSE_FILE% -p %PROJECT% images
:: docker network inspect %PROJECT%_net
pause
