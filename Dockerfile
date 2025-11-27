# Usamos JDK 21 ligero
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos los archivos de Gradle necesarios
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Copiamos el código fuente
COPY src ./src

# Construimos el proyecto (Gradle wrapper incluido)
RUN ./gradlew build --no-daemon

# Copiamos el JAR generado a un nombre fijo
COPY build/libs/*.jar app.jar

# Exponemos el puerto 8080 (solo de referencia; Render usará $PORT)
EXPOSE 8080

# Ejecutamos la app usando el puerto que Render asigna
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
