FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia os arquivos do projeto para /app
COPY ./app /app

# Dá permissão para o mvnw ser executável
RUN chmod +x ./mvnw

# Roda o build, gerando o jar dentro de target/
RUN ./mvnw package -DskipTests

# Opcional: limpa cache do Maven para imagem menor
RUN rm -rf ~/.m2/repository

# Aqui você deve substituir pelo nome exato do jar gerado
CMD ["java", "-jar", "target/app-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]
