FROM maven:latest AS build
COPY pom.xml /usr/rest-USER/pom.xml
COPY src /usr/rest-USER/src
WORKDIR /usr/rest-USER/
RUN mvn -f /usr/rest-USER/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/rest-USER/target/restuser-0.0.1-SNAPSHOT.jar /usr/lib/restuser.jar
ENTRYPOINT ["java","-jar","/usr/lib/restuser.jar"]
