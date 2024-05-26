# Use the latest Alpine Linux image
FROM alpine:latest

# Install OpenJDK JRE
RUN apk update && apk add --no-cache openjdk17-jre

# Set the working directory
WORKDIR /app

EXPOSE 8081

COPY target/dataservice-*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]