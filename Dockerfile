FROM eclipse-temurin:17-jre-alpine

WORKDIR /opt

ENV SERVER_PORT=9091

COPY target/*.jar /opt/app.jar

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]