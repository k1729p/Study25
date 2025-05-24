@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-24
set M2_HOME=c:\\tools\\apache-maven-3.9.9
set MAVEN_OPTS="--enable-native-access=ALL-UNNAMED"
::set SKIP_TESTS=-DskipTests
set SPRING_PROFILES_ACTIVE=local

set SERVER_PROJECTS=company,resource-server-common,resource-server-a,resource-server-b
call %M2_HOME%\bin\mvn -f ..\pom.xml %SKIP_TESTS% ^
  --projects %SERVER_PROJECTS% clean install

set PROJECT=company
echo -------------------------------------------------------------------------- %PROJECT%
start "%PROJECT%" /MAX %M2_HOME%\bin\mvn -f ..\pom.xml --quiet ^
  --projects %PROJECT% spring-boot:run

set PROJECT=resource-server-a
echo -------------------------------------------------------------------------- %PROJECT%
start "%PROJECT%" /MAX %M2_HOME%\bin\mvn -f ..\pom.xml --quiet ^
  --projects %PROJECT% spring-boot:run

set PROJECT=resource-server-b
echo -------------------------------------------------------------------------- %PROJECT%
start "%PROJECT%" /MAX %M2_HOME%\bin\mvn -f ..\pom.xml --quiet ^
  --projects %PROJECT% spring-boot:run
pause