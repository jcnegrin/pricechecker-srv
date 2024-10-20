# Etapa 1: Build de la aplicación
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final con Java y Playwright
FROM mcr.microsoft.com/playwright/java:latest

# Crear directorio de la app
WORKDIR /app

# Copiar el jar del build
COPY --from=build /app/target/project-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Instalar Node.js y las dependencias de Playwright
RUN apt-get update && apt-get install -y xvfb curl && \
    curl -sL https://deb.nodesource.com/setup_16.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g playwright && \
    playwright install-deps

# Exponer el puerto 8080 (Spring Boot)
EXPOSE 8080

# Comando para ejecutar la aplicación con xvfb
CMD ["xvfb-run", "--auto-servernum", "--server-args='-screen 0 1920x1080x24'", "java", "-jar", "/app/myapp.jar"]