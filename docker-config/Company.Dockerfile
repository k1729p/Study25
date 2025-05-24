FROM maven:3.9.9-eclipse-temurin-24 AS maven_tool
COPY company/src /tmp/company/src/
COPY pom.xml /tmp/pom.xml
COPY company/pom.xml /tmp/company/pom.xml
COPY resource-server-common/pom.xml /tmp/resource-server-common/pom.xml
COPY resource-server-a/pom.xml /tmp/resource-server-a/pom.xml
COPY resource-server-b/pom.xml /tmp/resource-server-b/pom.xml
COPY gui-client/pom.xml /tmp/gui-client/pom.xml
WORKDIR /tmp/
RUN mvn -f pom.xml --projects company install -DskipTests

FROM eclipse-temurin:24
COPY --from=maven_tool /tmp/company/target/Study25-company-1.0.0-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","application.jar"]