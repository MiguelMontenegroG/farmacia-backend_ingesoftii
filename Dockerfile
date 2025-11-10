# Usar imagen oficial de OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine AS builder

# Instalar dependencias necesarias
RUN apk add --no-cache bash

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos de configuración de Gradle
COPY build.gradle settings.gradle ./

# Copiar el código fuente
COPY src/ src/

# Compilar la aplicación
RUN ./gradlew build --no-daemon -x test

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine

# Instalar dependencias necesarias
RUN apk add --no-cache bash

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado en la etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar

# Exponer el puerto en el que la aplicación correrá
EXPOSE ${PORT:-8080}

# Ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]