# Usar imagen oficial de OpenJDK 17 (versión completa en lugar de Alpine)
FROM eclipse-temurin:17-jdk

# Instalar Gradle versión 7.6 (compatible con Spring Boot 3.2.0) y herramientas de red
RUN apt-get update && \
    apt-get install -y wget unzip dnsutils && \
    wget https://services.gradle.org/distributions/gradle-7.6-bin.zip && \
    unzip gradle-7.6-bin.zip && \
    mv gradle-7.6 /opt/gradle && \
    rm gradle-7.6-bin.zip && \
    rm -rf /var/lib/apt/lists/*

# Establecer variables de entorno para Gradle
ENV GRADLE_HOME=/opt/gradle
ENV PATH=$PATH:$GRADLE_HOME/bin

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar todos los archivos del proyecto
COPY . .

# Compilar la aplicación con Gradle excluyendo tests y crear un JAR ejecutable
RUN gradle bootJar -x test --no-daemon --stacktrace

# Listar los archivos JAR generados para depuración
RUN ls -la build/libs/

# Exponer el puerto en el que la aplicación correrá
EXPOSE ${PORT:-8080}

# Ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -Dsun.net.spi.nameservice.provider.1=dns,sun -Dsun.net.spi.nameservice.nameservers=8.8.8.8,8.8.4.4 -jar build/libs/FarmaciaIngeSonftII-0.0.1-SNAPSHOT.jar"]