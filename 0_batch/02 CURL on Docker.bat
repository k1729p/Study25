@echo off
set PROJECT=study25

:menu
set KEY=
set LABEL=
echo TIME[%TIME%]
echo Call GraphQL Endpoint with Curl on Docker.
echo.
echo [A] Application 'company'
echo [B] Application 'resource-server-a'
echo [C] Application 'resource-server-b'
echo any other key quits
set /P KEY="Select the key: "
if /i "%KEY:~0,1%"=="A" (
  set LABEL=[A] Application 'company'
  call :AAA
) else if /i "%KEY%"=="B" (
  set LABEL=[B] Application 'resource-server-a'
  call :BBB
) else if /i "%KEY%"=="C" (
  set LABEL=[C] Application 'resource-server-b'
  call :CCC
) else (
  goto :eof
)
goto menu
REM =================================================================================================================================================
:AAA
cls
docker run --network %PROJECT%_net --rm %PROJECT%-graphql-client ^
 /app/call_graphql_endpoint_company.sh
call :RedLabelAndPause
cls
goto :eof
REM =================================================================================================================================================
:BBB
cls
docker run --network %PROJECT%_net --rm %PROJECT%-graphql-client ^
 /app/call_graphql_endpoint_resource_server.sh resource-server-a 8082
call :RedLabelAndPause
cls
goto :eof
REM =================================================================================================================================================
:CCC
cls
docker run --network %PROJECT%_net --rm %PROJECT%-graphql-client ^
 /app/call_graphql_endpoint_resource_server.sh resource-server-b 8083
call :RedLabelAndPause
cls
goto :eof
REM =================================================================================================================================================
:RedLabelAndPause
powershell -Command Write-Host "FINISH %LABEL%" -foreground "Red"
pause
goto :eof
REM =================================================================================================================================================
:quit
echo.&pause