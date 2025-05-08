# IMAGEN BASE
FROM eclipse-temurin:24.0.1_9-jdk

# Directorio de trabajo en el contenedor
WORKDIR /serviapp

# Copiar archivos necesarios para descargar dependencias
COPY ./pom.xml /serviapp  
COPY ./.mvn /serviapp/.mvn
COPY ./mvnw /serviapp

# Hacer ejecutable el wrapper
RUN chmod +x mvnw

# Descargar dependencias offline
RUN ./mvnw dependency:go-offline

# Copiar código fuente
COPY ./src /serviapp/src

# Compilar el proyecto (sin tests)
RUN ./mvnw clean install -DskipTests

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "target/ServiApp-0.0.1-SNAPSHOT.jar"]





















