# Usa una imagen de Java 17 (puedes cambiarla según tu versión)
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR generado por Maven/Gradle
COPY target/inditex-app-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto donde corre la app (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
