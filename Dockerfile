FROM amazoncorretto:8
WORKDIR /app
COPY ./target/timesheet2021-3.0.jar /app/
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
CMD ["java", "-jar" ,"timesheet2021-3.0.jar"]
