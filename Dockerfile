# Usar imagen oficial de OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# Instalar Gradle
RUN apk add --no-cache bash gradle

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar todos los archivos del proyecto
COPY . .

# Compilar la aplicación con Gradle excluyendo tests
RUN gradle build -x test --no-daemon

# Exponer el puerto en el que la aplicación correrá
EXPOSE ${PORT:-8080}

# Ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-Dserver.port=${PORT:-8080}", "-jar", "build/libs/*.jar"]