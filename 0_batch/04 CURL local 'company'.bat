@echo off
set SITE=http://localhost:9091/graphql
set CURL=c:\tools\curl-7.58.0\bin\curl.exe
set CURL=%CURL% -s -g -H "Accept: application/json" -H "Content-Type: application/json"
set JQ=d:\tools\jq\jq-win64.exe
set NO_COLOR=Y
set QUERY_DIR=../docker-config/tests/queries/company
set HR_YELLOW=@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
set HR_RED=@powershell    -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"

:mutateDepartments
%HR_YELLOW%
powershell -Command Write-Host "MUTATE DEPARTMENTS" -foreground "Green"
%CURL% -X POST %SITE% -d @%QUERY_DIR%/mutateDepartments.txt
echo.

:queryAllDepartments
%HR_YELLOW%
powershell -Command Write-Host "QUERY ALL DEPARTMENTS" -foreground "Green"
%CURL% -X POST %SITE% -d @%QUERY_DIR%/queryDepartments.txt | %JQ% .
echo.

:queryDepartmentById
%HR_YELLOW%
powershell -Command Write-Host "QUERY DEPARTMENT BY ID" -foreground "Green"
%CURL% -X POST %SITE% -d @%QUERY_DIR%/queryDepartmentById.txt | %JQ% .
echo.

:queryEmployeesCountByTitle
%HR_YELLOW%
powershell -Command Write-Host "QUERY EMPLOYEES COUNT BY TITLE" -foreground "Green"
%CURL% -X POST %SITE% -d @%QUERY_DIR%/queryEmployeesCountByTitle.txt | %JQ% .
echo.

:finish
%HR_RED%
powershell -Command Write-Host "FINISH" -foreground "Red"
pause