# =============================
# 1 Build stage
# =============================
FROM gradle:8.7-jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle clean bootJar -x test --no-daemon

# =============================
# 2 Runtime stage
# =============================
FROM openjdk:17-jdk-slim

# Variable de entorno PORT usada por Render
ENV PORT=8080

# Copiamos el jar generado
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Exponemos el puerto dinámico
EXPOSE ${PORT}

# Ejecutamos la app
ENTRYPOINT ["java", "-jar", "/app.jar"]
