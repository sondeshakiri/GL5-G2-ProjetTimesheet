FROM openjdk:17-jdk-alpine
EXPOSE 8080
ADD target/timesheet2021-3.0.jar timesheet2021-3.0.jar
ENTRYPOINT ["java","-jar","/timesheet2021-3.0.jar"]