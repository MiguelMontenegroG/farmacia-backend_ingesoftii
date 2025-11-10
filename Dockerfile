#
# 1️⃣ Build stage
#
FROM gradle:8.7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar --no-daemon

#
# 2️⃣ Runtime stage
#
FROM eclipse-temurin:17-jdk

# Variable de entorno del puerto (Render asigna uno dinámico)
ENV PORT=8080

# Copiar el jar generado
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Exponer el puerto (para Render)
EXPOSE ${PORT}

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "/app.jar"]
