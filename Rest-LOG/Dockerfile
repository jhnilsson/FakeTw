FROM maven:latest AS build
COPY pom.xml /usr/rest-log/pom.xml
COPY src /usr/rest-log/src
WORKDIR /usr/rest-log/
RUN mvn -f /usr/rest-log/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/rest-log/target/restlog-0.0.1-SNAPSHOT.jar /usr/lib/restlog.jar
ENTRYPOINT ["java","-jar","/usr/lib/restlog.jar"]
