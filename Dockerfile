FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/spring-rest-boot-api-1.0.0.jar api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/api.jar"]