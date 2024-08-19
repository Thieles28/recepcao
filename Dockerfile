# Use uma imagem base do Maven com OpenJDK 17 para compilar o projeto
FROM maven:3.9.2-eclipse-temurin-17 as builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o pom.xml e as dependências Maven para dentro do contêiner
COPY pom.xml .
COPY src ./src

# Baixa as dependências e compila o projeto
RUN mvn clean package -DskipTests

# Cria uma nova imagem base com OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho no contêiner final
WORKDIR /app

# Copia o JAR gerado do estágio anterior
COPY --from=builder /app/target/clientes-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080, onde a aplicação Spring Boot será executada
EXPOSE 8080

# Define a variável de ambiente para o MySQL
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/clientes?useSSL=false&serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=1234

# Comando para executar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]
