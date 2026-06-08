# ===== Estagio 1: build com Maven + JDK 21 =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
RUN mvn -q clean package -DskipTests

# ===== Estagio 2: imagem final, so com o JRE 21 =====
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN mkdir -p /app/uploads
# MaxRAMPercentage faz o JVM respeitar o limite de RAM do container (evita OOM / exit 137)
ENTRYPOINT ["sh", "-c", "java -XX:MaxRAMPercentage=75.0 -jar app.jar"]
