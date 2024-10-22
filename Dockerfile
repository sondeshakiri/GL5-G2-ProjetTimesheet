FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/timesheet2021-3.0.jar timesheet2021.jar
ENTRYPOINT ["java", "-jar", "/timesheet2021.jar"]
