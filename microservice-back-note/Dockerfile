# Étape 1: Construire le projet avec Maven
FROM maven:3.8.4-openjdk-17-slim AS build
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn clean package -DskipTests

# Étape 2: Construire l'image Docker du service Spring Boot
FROM openjdk:17-slim
COPY --from=build /tmp/target/microservice-back-note-0.0.1-SNAPSHOT.jar app.jar

# Définir une variable d'environnement pour le profil Spring
# La valeur par défaut est 'prod', mais cela peut être modifié lors du démarrage du conteneur
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 9003
ENTRYPOINT ["java","-jar","/app.jar"]
