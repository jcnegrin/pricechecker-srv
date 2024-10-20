# Etapa 1: Build de la aplicación
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final con Java y Playwright
FROM mcr.microsoft.com/playwright/java:latest

# Crear directorio de la app
WORKDIR /app

# Copiar el jar del build
COPY --from=build /app/target/myapp.jar /app/myapp.jar

# Instalar Playwright y navegadores necesarios
RUN npx playwright install-deps && npx playwright install

# Exponer el puerto 8080 (Spring Boot)
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/myapp.jar"]
