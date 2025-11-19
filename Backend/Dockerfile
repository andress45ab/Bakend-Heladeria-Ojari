# 1. Etapa de Construcción (Build Stage)
# Usamos una imagen que tiene Maven y la versión 21 de OpenJDK
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el pom.xml y las fuentes
COPY pom.xml .
COPY src /app/src

# Construye el proyecto y genera el JAR
RUN mvn clean install -DskipTests

# 2. Etapa de Ejecución (Run Stage)
# Usamos una imagen de Java 21 más ligera para ejecutar
FROM eclipse-temurin:21-jre-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo .jar con el nombre exacto que me has confirmado
COPY --from=build /app/target/Backend-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto que usa Spring Boot (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]