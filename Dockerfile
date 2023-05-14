# Base image
FROM openjdk:17

COPY target/ETIVR-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/ETIVR-1.0-SNAPSHOT.jar"]