FROM maven:latest AS build
COPY pom.xml /usr/rest-PM/pom.xml
COPY src /usr/rest-PM/src
WORKDIR /usr/rest-PM/
RUN mvn -f /usr/rest-PM/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/rest-PM/target/restpm-0.0.1-SNAPSHOT.jar /usr/lib/restpm.jar
ENTRYPOINT ["java","-jar","/usr/lib/restpm.jar"]
