# ===== Estagio 1: build com Maven + JDK 21 =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Cache de dependencias: copia so o pom primeiro
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copia o codigo e gera o JAR
COPY src ./src
RUN mvn -q clean package -DskipTests

# ===== Estagio 2: imagem final, so com o JRE 21 =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o JAR gerado no estagio de build
COPY --from=build /app/target/*.jar app.jar

# Pasta de uploads (pode receber um volume do Railway)
RUN mkdir -p /app/uploads

# MaxRAMPercentage faz o JVM respeitar o limite de RAM do container
# (evita o crash de OOM / exit 137 em instancias pequenas)
ENTRYPOINT ["sh", "-c", "java -XX:MaxRAMPercentage=75.0 -jar app.jar"]
