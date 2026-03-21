FROM openjdk:17-ea-alpine

ARG JAVA_FILE=target/*.jar
COPY ${JAVA_FILE} payment-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Doracle.jdbc.timezoneAsRegion=false","-jar", "payment-service.jar"]
EXPOSE 8899
