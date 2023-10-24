FROM openjdk:17
ARG JAR_FILE=ms-products/target/*.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]v/./urandom", "-jar", "/app.jar"]
