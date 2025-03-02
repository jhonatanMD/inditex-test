# Prices Project

## Descripción
Este proyecto implementa un servicio de gestión de precios basado en Java 17, utilizando H2 como base de datos en memoria. Se ha desarrollado con Spring Boot y JPA para la gestión de datos.

## Tecnologías Utilizadas:
- **Java 17**: Lenguaje de programación principal.
- **Spring Boot**: Framework para el desarrollo de la aplicación.
- **JPA (Jakarta Persistence API)**: Para la persistencia de datos.
- **H2 Database**: Base de datos en memoria para pruebas y desarrollo.
- **Swagger & OpenAPI**: Documentación de la API y generación automática de controladores y DTOs.
- **SonarQube**: Análisis de calidad de código.
- **GitHub Actions**: Automatización del despliegue.
- **Docker & Docker Compose**: Contenerización y orquestación de servicios.

## Arquitectura
Se ha seguido una **arquitectura hexagonal**, donde la lógica de negocio está desacoplada de las interfaces externas como controladores y persistencia.

## Despliegue
El proyecto se despliega en una máquina virtual en **Google Cloud Platform (GCP)** utilizando **GitHub Actions**. SonarQube también está instalado en la VM para el análisis de calidad del código.

## Documentación
La API está documentada con **Swagger** y **OpenAPI**, permitiendo la generación automática de los controladores y DTOs.

### Endpoints:
1. **Consulta de Precios**

   Este endpoint permite obtener los precios de un producto en una fecha y hora específica, de acuerdo con el `productId` y `brandId` proporcionados.

   **URL**:
   GET http://35.209.236.15:8080/prices

   **Parámetros de consulta**:
   - **applicationDate** (requerido): Fecha y hora en formato `YYYY-MM-DDTHH:MM:SS` (ejemplo: `2025-06-14T15:01:00`).
     - **productId** (requerido): El ID del producto.
     - **brandId** (requerido): El ID de la marca.

   **Ejemplo de solicitud**:
   ```http
   GET http://35.209.236.15:8080/prices?applicationDate=2025-06-14T15:01:00&productId=35455&brandId=1
   ```

Acceder a la documentación en producción:
- [Swagger UI](http://35.209.236.15:8080/swagger-ui/index.html)
- [SonarQube Dashboard](http://35.209.236.15:9000/projects)
- [Consulta de precios en producción](http://35.209.236.15:8080/prices?applicationDate=2025-06-14T15:01:00&productId=35455&brandId=1)

## Ejecución Local
Para ejecutar el proyecto localmente, asegúrate de tener instalado **Java 17, Maven y Docker**.

Compila el proyecto con:
```sh
mvn clean package
```

Ejecuta la aplicación:
```sh
java -jar target/inditex-app.jar
```


Acceder a los servicios en local:
- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- [SonarQube Dashboard](http://localhost:9000/dashboard?id=inditex-app)
- [Consulta de precios en local](http://localhost:8080/prices?applicationDate=2025-06-14T15:01:00&productId=35455&brandId=1)

## Configuración de Docker Compose
El proyecto utiliza **Docker Compose** para definir y administrar los servicios de la aplicación, SonarQube y PostgreSQL.

Archivo `docker-compose.yml`:
```yaml
version: "3.8"

services:
  app:
    build: .
    container_name: inditex-app
    ports:
      - "8080:8080"
    environment:
      SONAR_TOKEN: sqa_6fe80eef8620317ddaf14fbdd8e7a5fb17fd3c83
    networks:
      - sonarnet
    depends_on:
      - sonarqube

  postgres:
    image: postgres:15
    container_name: sonar-postgres
    restart: unless-stopped
    environment:
      POSTGRES_USER: sonar
      POSTGRES_PASSWORD: sonar
      POSTGRES_DB: sonarqube
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - sonarnet

  sonarqube:
    image: sonarqube:lts-community
    container_name: sonarqube
    restart: unless-stopped
    depends_on:
      - postgres
    environment:
      SONAR_JDBC_URL: jdbc:postgresql://postgres:5432/sonarqube
      SONAR_JDBC_USERNAME: sonar
      SONAR_JDBC_PASSWORD: sonar
    ports:
      - "9000:9000"
    networks:
      - sonarnet

volumes:
  postgres_data:

networks:
  sonarnet:
```

Este archivo define tres servicios:
- **app**: Contenedor de la aplicación Spring Boot.
- **postgres**: Base de datos PostgreSQL utilizada por SonarQube.
- **sonarqube**: Servicio de análisis de código estático.

Para levantar los servicios, ejecutar:
```sh
docker-compose up -d
```

## Comandos para ejecutar el análisis

Para ejecutar el análisis de SonarQube en este proyecto, puedes usar uno de los siguientes comandos Maven:

### Con credenciales (usuario y contraseña)
Ejecuta la aplicación:
```bash
mvn clean verify sonar:sonar -Dsonar.projectKey=inditex-app -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin1234
```

o

```bash
mvn clean verify sonar:sonar -Dsonar.projectKey=inditex-app -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
```

## Dockerfile
El **Dockerfile** de la aplicación permite construir la imagen de Docker con el entorno adecuado.

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/inditex-app.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```




Este Dockerfile usa una imagen base de OpenJDK 17, copia el JAR de la aplicación y lo expone en el puerto 8080.
