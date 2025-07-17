FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY ./app /app

RUN chmod +x mvnw
RUN ./mvnw package -DskipTests

CMD ["java", "-jar", "target/app-*.jar"]
