FROM openjdk:11-jdk-slim
ENV SPRING_PROFILES_ACTIVE docker
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD ["java", "-jar", "/app.jar"]
