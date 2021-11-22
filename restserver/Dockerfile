FROM maven:latest AS build
COPY pom.xml /usr/pom.xml
COPY src /usr/src
WORKDIR /usr/
RUN mvn -f /usr/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/target/restserver-0.0.1-SNAPSHOT.jar /usr/lib/restserver.jar
ENTRYPOINT ["java","-jar","/usr/lib/restserver.jar"]
