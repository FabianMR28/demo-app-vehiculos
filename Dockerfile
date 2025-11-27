# Usamos JDK 21 ligero
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos Gradle wrapper y le damos permisos de ejecución
COPY gradlew ./
COPY gradle ./gradle
RUN chmod +x gradlew

# Copiamos archivos de Gradle y código fuente
COPY build.gradle settings.gradle ./
COPY src ./src

# Construimos el proyecto dentro del contenedor
RUN ./gradlew clean build --no-daemon

# Copiamos el JAR generado a un nombre fijo
COPY build/libs/*.jar app.jar

# Exponemos puerto de referencia (Render usará $PORT)
EXPOSE 8080

# Ejecutamos la app usando el puerto asignado por Render
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
