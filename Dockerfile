FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiamos Gradle wrapper y le damos permisos
COPY gradlew ./
COPY gradle ./gradle
RUN chmod +x gradlew

# Copiamos archivos de Gradle y código fuente
COPY build.gradle settings.gradle ./
COPY src ./src

# Construimos el proyecto dentro del contenedor
RUN ./gradlew clean build --no-daemon

# Exponemos puerto de referencia (Render usará $PORT)
EXPOSE 8080

# Ejecutamos el JAR directamente desde build/libs
ENTRYPOINT ["sh", "-c", "java -jar build/libs/demo-app-vehiculos-0.0.1-SNAPSHOT.jar --server.port=${PORT}"]
