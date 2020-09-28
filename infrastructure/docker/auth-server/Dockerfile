FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=auth-server/target/*.jar
COPY ${JAR_FILE} auth-server.jar
ENTRYPOINT ["java","-jar","/auth-server.jar"]