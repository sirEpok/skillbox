FROM openjdk:17
RUN mkdir /app
COPY /build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","/app/demo-0.0.1-SNAPSHOT.jar"]