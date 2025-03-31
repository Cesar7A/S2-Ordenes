# Usa una imagen base con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de Maven para descargar dependencias primero (cache eficiente)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copia el resto del código fuente
COPY src ./src

# Compila la aplicación
RUN ./mvnw clean package -DskipTests

# Expone el puerto 8080 dentro del contenedor
EXPOSE 8080

# Ejecuta el archivo .jar generado
ENTRYPOINT ["java", "-jar", "target/ordenes-service-0.0.1-SNAPSHOT.jar"]
