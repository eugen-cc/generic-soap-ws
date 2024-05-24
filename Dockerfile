FROM openjdk:17-slim
LABEL authors="Eugen Gross"

EXPOSE 8081

COPY target/dataservice-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "dataservice-0.0.1-SNAPSHOT.jar"]