@echo off
set SITE=http://localhost:%1/graphql
set CURL=c:\tools\curl-7.58.0\bin\curl.exe
set CURL=%CURL% -s -g -H "Accept: application/json" -H "Content-Type: application/json"
set JQ=d:\tools\jq\jq-win64.exe
set NO_COLOR=Y
set QUERY_DIR=../docker-config/tests/queries/resource-server
set HR_YELLOW=@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
set HR_RED=@powershell    -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"
set PASSWORD=clandestine
setlocal enabledelayedexpansion

set USERS=alice bob charlie david
set LEVELS=Secret Confidential Restricted Official
set i=0
for %%U in (%USERS%) do (
    set /a i+=1
    set USER[!i!]=%%U
)
set i=0
for %%L in (%LEVELS%) do (
    set /a i+=1
    set LEVEL[!i!]=%%L
)

:mutateDocument
for /l %%I in (1,1,4) do (
    %HR_YELLOW%
    powershell -Command Write-Host "MUTATE DOCUMENT WITH USER !USER[%%I]! AND LEVEL !LEVEL[%%I]!" -foreground "Green"
    %CURL% -X POST %SITE%  -u "!USER[%%I]!:%PASSWORD%" -d "@%QUERY_DIR%/mutateDocument_!LEVEL[%%I]!.txt"
    @echo.
)

:queryDocument
for /l %%I in (1,1,4) do (
    %HR_YELLOW%
    powershell -Command Write-Host "QUERY DOCUMENT WITH USER !USER[%%I]! AND LEVEL !LEVEL[%%I]!" -foreground "Green"
    %CURL% -X POST %SITE%  -u "!USER[%%I]!:%PASSWORD%" -d "@%QUERY_DIR%/queryDocument_!LEVEL[%%I]!.txt" | %JQ% .
    echo.
)

:queryDocumentWithUnauthorizedUser
set USERS=bob charlie david
for %%U in (%USERS%) do (
    %HR_YELLOW%
    powershell -Command Write-Host "QUERY DOCUMENT WITH USER %%U AND LEVEL Secret" -foreground "Red"
    %CURL% -X POST %SITE%  -u "%%U:%PASSWORD%" -d "@%QUERY_DIR%/queryDocument_Secret.txt" | %JQ% .
    echo.
)

:finish
%HR_RED%
powershell -Command Write-Host "FINISH" -foreground "Red"